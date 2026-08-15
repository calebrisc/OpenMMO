#!/usr/bin/env python3
"""Build a dialog table for Johto out of the HeartGold decomp's message archives.

The GBA path reads its text out of the player's ROM and addresses each line by the byte offset it
sits at, packing the region into the top nibble. DS text is not laid out that way: it lives in
numbered NARC message archives and is addressed by (archive, row index). This writes the same
committed-table shape the GBA regions use, with the id packed as:

    region << 28 | archive << 16 | index

The region nibble matches the GBA scheme. The archive/index packing is OUR choice and is NOT yet
verified against what the retail client expects for a DS region -- see the note written into the
file. Everything else here is faithful decomp data.

Usage: hg_dialog_extract.py <decomp-dir> <out.json> [--region-wire N] [--region NAME]
"""

from __future__ import annotations

import argparse
import json
import pathlib
import re
import sys
import xml.etree.ElementTree as ET

ARCHIVE_RE = re.compile(r"^msg_(\d{4})")

# Mirrors REGION_SHIFT in the GBA dialog generator.
REGION_SHIFT = 28
ARCHIVE_SHIFT = 16
MAX_INDEX = (1 << ARCHIVE_SHIFT) - 1


def script_archives(decomp: pathlib.Path) -> set[int]:
    """The archives the field scripts actually #include, which is what a ported script cites."""
    seq = decomp / "files" / "fielddata" / "script" / "scr_seq"
    wanted = set()
    for path in seq.glob("*.s"):
        for match in re.finditer(r"msgdata/msg/msg_(\d{4})", path.read_text(errors="replace")):
            wanted.add(int(match.group(1)))
    return wanted


def extract(decomp: pathlib.Path, region_wire: int, only: set[int] | None):
    msg_dir = decomp / "files" / "msgdata" / "msg"
    if not msg_dir.is_dir():
        sys.exit(f"no message archives under {msg_dir}")

    lines = []
    skipped_archives = []
    for path in sorted(msg_dir.glob("*.gmm")):
        match = ARCHIVE_RE.match(path.name)
        if not match:
            skipped_archives.append(path.name)
            continue
        archive = int(match.group(1))
        if only is not None and archive not in only:
            continue
        try:
            root = ET.parse(path).getroot()
        except ET.ParseError as exc:
            skipped_archives.append(f"{path.name} ({exc})")
            continue
        for row in root.findall("row"):
            label = row.get("id")
            index_text = row.get("index")
            if label is None or index_text is None:
                continue
            index = int(index_text)
            if index > MAX_INDEX:
                skipped_archives.append(f"{path.name} row {index} exceeds the index field")
                continue
            english = None
            for language in row.findall("language"):
                if language.get("name") == "English":
                    english = language.text or ""
                    break
            if english is None:
                continue
            lines.append(
                {
                    "label": label,
                    "id": (region_wire << REGION_SHIFT) | (archive << ARCHIVE_SHIFT) | index,
                    "text": english,
                }
            )
    return lines, skipped_archives


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("decomp")
    parser.add_argument("out")
    parser.add_argument("--region", default="johto")
    parser.add_argument(
        "--all-archives",
        action="store_true",
        help="Include every archive, not just the ones field scripts reference.",
    )
    parser.add_argument(
        "--region-wire",
        type=int,
        default=2,
        help="The region's wire value. UNCONFIRMED for Johto; the client numbers the DS regions "
        "2, 3, 4 and 10 and we do not yet know which is which.",
    )
    args = parser.parse_args()

    decomp = pathlib.Path(args.decomp)
    only = None if args.all_archives else script_archives(decomp)
    lines, skipped = extract(decomp, args.region_wire, only)
    if not lines:
        sys.exit("extracted nothing")

    duplicates = len(lines) - len({line["label"] for line in lines})
    body = {
        "region": args.region,
        "gameCode": "IPKE",
        "note": (
            "Extracted from the pokeheartgold decomp's .gmm message archives. The id packs "
            "region << 28 | archive << 16 | row index. The archive/index packing is unverified "
            "against the retail client, which addresses GBA text by ROM offset instead; the "
            "region wire value is likewise unconfirmed."
        ),
        "lines": lines,
    }
    out = pathlib.Path(args.out)
    out.parent.mkdir(parents=True, exist_ok=True)
    out.write_text(json.dumps(body, indent=4, ensure_ascii=False) + "\n")

    scope = "every archive" if only is None else f"{len(only)} script-referenced archives"
    print(f"lines={len(lines)} duplicate-labels={duplicates} skipped={len(skipped)} ({scope})")
    for note in skipped[:10]:
        print(f"  skipped: {note}")


if __name__ == "__main__":
    main()
