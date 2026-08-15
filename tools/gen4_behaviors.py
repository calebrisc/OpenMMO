#!/usr/bin/env python3
"""Lift the Gen 4 tile behaviour dictionary out of the Platinum decomp.

HeartGold ships the same terrain attribute data but names only five of its 256 behaviours, so the
meaning of the rest cannot be read out of that decomp at all. Platinum names 108 of them and, more
usefully, carries a table saying which generate wild encounters and which can be surfed.

That table applies to HeartGold because the numbering is engine level rather than per game:
WATERFALL is 19 and TOWN_MAP is 133 in both, and the two behaviours HeartGold names which Platinum
marks unused (HEADBUTT at 6, WHIRLPOOL at 17) already carry exactly the flags those mechanics need.

Usage: gen4_behaviors.py <pokeplatinum-dir> <out.json>
"""

from __future__ import annotations

import json
import pathlib
import re
import sys

SURFABLE = 1 << 0
ENCOUNTER = 1 << 1


def read_enum(platinum: pathlib.Path) -> dict[int, str]:
    text = (platinum / "include" / "constants" / "field" / "map_tile_behaviors.h").read_text()
    match = re.search(r"enum\s*\w*\s*\{(.*?)\};", text, re.S)
    if not match:
        sys.exit("could not find the tile behaviour enum")
    names: dict[int, str] = {}
    value = 0
    for raw in match.group(1).split(","):
        entry = raw.split("//")[0].strip()
        if not entry:
            continue
        if "=" in entry:
            name, literal = (part.strip() for part in entry.split("="))
            value = int(literal, 0)
        else:
            name = entry
        names[value] = name
        value += 1
    return names


def read_flags(platinum: pathlib.Path) -> dict[str, int]:
    text = (platinum / "src" / "map_tile_behavior.c").read_text()
    flags: dict[str, int] = {}
    for name, expression in re.findall(r"\[(TILE_BEHAVIOR_\w+)\]\s*=\s*([^,\n]+)", text):
        value = 0
        if "SURFABLE_ENCOUNTER" in expression:
            value = SURFABLE | ENCOUNTER
        else:
            if "FLAG_SURFABLE" in expression:
                value |= SURFABLE
            if "FLAG_ENCOUNTER" in expression:
                value |= ENCOUNTER
        flags[name] = value
    return flags


def main():
    if len(sys.argv) < 3:
        sys.exit(__doc__)
    platinum = pathlib.Path(sys.argv[1])
    out = pathlib.Path(sys.argv[2])

    names = read_enum(platinum)
    flags = read_flags(platinum)

    behaviours = []
    for value in range(256):
        name = names.get(value)
        flag = flags.get(name, 0) if name else 0
        behaviours.append(
            {
                "value": value,
                "name": name,
                "named": bool(name and "UNUSED" not in name),
                # Platinum's flags are only trustworthy for behaviours Platinum itself named.
                # For a slot it marks unused the flag is a guess, and HeartGold measurably
                # disagrees: behaviour 6 is flagged as generating encounters but is commoner on
                # maps with NO land encounters than on maps with them, so it is ordinary walkable
                # ground. Named grass, 2 and 3, correlates the right way and is kept.
                "encounter": bool(flag & ENCOUNTER) and bool(name and "UNUSED" not in name),
                "surfable": bool(flag & SURFABLE) and bool(name and "UNUSED" not in name),
                "flagsTrusted": bool(name and "UNUSED" not in name),
            }
        )

    body = {
        "source": "pokeplatinum",
        "note": (
            "Gen 4 tile behaviours, lifted from Platinum because HeartGold names only five of "
            "them. The attribute word is u16: bit 15 set means impassable, the low byte is the "
            "behaviour. Verified against HeartGold by the four behaviours it does name."
        ),
        "collisionMask": 0x8000,
        "behaviorMask": 0x00FF,
        "invalid": 255,
        "behaviors": behaviours,
    }
    out.parent.mkdir(parents=True, exist_ok=True)
    out.write_text(json.dumps(body, indent=2) + "\n")

    named = sum(1 for b in behaviours if b["named"])
    encounter = sum(1 for b in behaviours if b["encounter"])
    surfable = sum(1 for b in behaviours if b["surfable"])
    print(f"behaviours=256 named={named} encounter={encounter} surfable={surfable}")
    for value in (2, 3, 6, 8, 17, 19, 133):
        b = behaviours[value]
        print(f"  {value:3d} {b['name']} encounter={b['encounter']} surfable={b['surfable']}")


if __name__ == "__main__":
    main()
