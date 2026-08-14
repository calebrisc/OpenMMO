#!/usr/bin/env python3
"""Read HeartGold's terrain attributes out of the land-data archive.

The archive is shipped in the decomp as an unnamed blob with no build rule and no extraction tool,
which is why the collision data looked unreachable. It is a plain NARC: 676 members, each one a
32x32 block of tiles. Per member, skip 0x14 and read 0x800 bytes as 1024 little-endian u16s
(see include/terrain_attributes.h; the offset is 0x10 in Platinum and 0x14 here).

Each u16 is bit 15 = impassable, low byte = tile behaviour.

Usage: hg_terrain.py <decomp-dir> <out.json> [--blocks-out blocks.bin]
"""

from __future__ import annotations

import argparse
import collections
import json
import pathlib
import struct
import sys

LAND_DATA = ("files", "a", "0", "6", "5")
TERRAIN_OFFSET = 0x14
TERRAIN_SIZE = 0x800
TILES_X = 32
TILES_Z = 32
COLLISION_MASK = 0x8000
BEHAVIOR_MASK = 0x00FF


def read_narc(path: pathlib.Path) -> list[bytes]:
    """Members of a NARC. Nintendo writes each section magic reversed."""
    blob = path.read_bytes()
    if blob[:4] != b"NARC":
        sys.exit(f"{path} is not a NARC")
    header_size, sections = struct.unpack_from("<HH", blob, 12)
    if sections < 3:
        sys.exit(f"expected 3 sections, found {sections}")

    offset = header_size
    if blob[offset : offset + 4] != b"BTAF":
        sys.exit("no BTAF where one was expected")
    fatb_size, file_count = struct.unpack_from("<II", blob, offset + 4)
    entries = [
        struct.unpack_from("<II", blob, offset + 12 + index * 8) for index in range(file_count)
    ]

    offset += fatb_size
    if blob[offset : offset + 4] != b"BTNF":
        sys.exit("no BTNF where one was expected")
    (fntb_size,) = struct.unpack_from("<I", blob, offset + 4)

    offset += fntb_size
    if blob[offset : offset + 4] != b"GMIF":
        sys.exit("no GMIF where one was expected")
    image = offset + 8

    return [blob[image + start : image + end] for start, end in entries]


def decode_block(member: bytes):
    """One 32x32 block, or None when the member is too short to hold terrain."""
    if len(member) < TERRAIN_OFFSET + TERRAIN_SIZE:
        return None
    words = struct.unpack_from(f"<{TILES_X * TILES_Z}H", member, TERRAIN_OFFSET)
    return words


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("decomp")
    parser.add_argument("out")
    parser.add_argument("--blocks-out", help="Write the raw decoded grids alongside the summary.")
    args = parser.parse_args()

    path = pathlib.Path(args.decomp).joinpath(*LAND_DATA)
    members = read_narc(path)

    behaviour_counts = collections.Counter()
    impassable = 0
    total = 0
    short_members = 0
    blocks = []
    for index, member in enumerate(members):
        words = decode_block(member)
        if words is None:
            short_members += 1
            blocks.append(None)
            continue
        collisions = bytearray(TILES_X * TILES_Z)
        behaviours = bytearray(TILES_X * TILES_Z)
        for i, word in enumerate(words):
            blocked = 1 if word & COLLISION_MASK else 0
            behaviour = word & BEHAVIOR_MASK
            collisions[i] = blocked
            behaviours[i] = behaviour
            behaviour_counts[behaviour] += 1
            impassable += blocked
            total += 1
        blocks.append({"index": index, "collision": bytes(collisions), "behavior": bytes(behaviours)})

    summary = {
        "source": str(path),
        "members": len(members),
        "blocksDecoded": sum(1 for b in blocks if b is not None),
        "shortMembers": short_members,
        "tiles": total,
        "impassableTiles": impassable,
        "impassablePercent": round(100 * impassable / total, 2) if total else 0,
        "behaviorHistogram": {
            str(value): count for value, count in sorted(behaviour_counts.items())
        },
    }
    out = pathlib.Path(args.out)
    out.parent.mkdir(parents=True, exist_ok=True)
    out.write_text(json.dumps(summary, indent=2) + "\n")

    if args.blocks_out:
        # index (u16), then collision then behaviour, one byte per tile.
        raw = bytearray()
        for block in blocks:
            if block is None:
                continue
            raw += struct.pack("<H", block["index"])
            raw += block["collision"]
            raw += block["behavior"]
        pathlib.Path(args.blocks_out).write_bytes(bytes(raw))

    print(
        f"members={len(members)} decoded={summary['blocksDecoded']} short={short_members} "
        f"tiles={total} impassable={summary['impassablePercent']}%"
    )
    top = behaviour_counts.most_common(12)
    print("most common behaviours:", ", ".join(f"{v}×{c}" for v, c in top))


if __name__ == "__main__":
    main()
