#!/usr/bin/env python3
"""Join HeartGold's readable map sources into one table, keyed the way the server addresses maps.

Three decomp sources have to be joined before a DS region can be generated, and the short name in
the trailing comment of constants/maps.h is the only key common to all of them:

    include/constants/maps.h        MAP_ROUTE_29 = 33, short name R29
    src/data/map_headers.h          per-map weather, mapType, matrixId, eventsBank, region
    files/fielddata/eventdata/...   zone_event/NNN_<short>.json, the warps and objects

HeartGold numbers its maps flat to 540, and the server addresses a map as (region, bank, map) with
a byte each, so the id is split as bank = id >> 8, map = id & 0xFF. That split is OUR convention:
nothing in the server or the protocol knows it, and it still has to be checked against what the
retail client expects for a DS region.

Usage: hg_map_table.py <decomp-dir> <out.json>
"""

from __future__ import annotations

import json
import pathlib
import re
import sys

DEFINE_RE = re.compile(r"^#define\s+(MAP_[A-Z0-9_]+)\s+(\d+)\s*//\s*MAP_(\S+)\s*$")
ENTRY_RE = re.compile(r"\[(MAP_[A-Z0-9_]+)\]\s*=\s*\{(.*?)\n\s*\},", re.S)
FIELD_RE = re.compile(r"\.(\w+)\s*=\s*([^,\n]+?)\s*,?\s*$", re.M)
ZONE_RE = re.compile(r"^(\d+)_(.+)\.json$")


def read_ids(decomp: pathlib.Path):
    path = decomp / "include" / "constants" / "maps.h"
    ids = {}
    for line in path.read_text().splitlines():
        match = DEFINE_RE.match(line.strip())
        if match:
            name, number, short = match.groups()
            ids[name] = {"id": int(number), "name": name, "short": short}
    return ids


def read_headers(decomp: pathlib.Path):
    text = (decomp / "src" / "data" / "map_headers.h").read_text()
    headers = {}
    for match in ENTRY_RE.finditer(text):
        name, body = match.groups()
        headers[name] = {k: v.strip() for k, v in FIELD_RE.findall(body)}
    return headers


def read_zone_events(decomp: pathlib.Path):
    """Short name -> zone_event file, so a map can be joined to its warps and objects."""
    zone_dir = decomp / "files" / "fielddata" / "eventdata" / "zone_event"
    by_short = {}
    for path in sorted(zone_dir.glob("*.json")):
        match = ZONE_RE.match(path.name)
        if match:
            by_short.setdefault(match.group(2), path.name)
    return by_short


def main():
    if len(sys.argv) < 3:
        sys.exit(__doc__)
    decomp = pathlib.Path(sys.argv[1])
    out = pathlib.Path(sys.argv[2])

    ids = read_ids(decomp)
    headers = read_headers(decomp)
    zones = read_zone_events(decomp)

    maps = []
    missing_header = 0
    missing_zone = 0
    for entry in sorted(ids.values(), key=lambda e: e["id"]):
        header = headers.get(entry["name"])
        if header is None:
            missing_header += 1
        zone = zones.get(entry["short"])
        if zone is None:
            missing_zone += 1
        maps.append(
            {
                "id": entry["id"],
                "name": entry["name"],
                "short": entry["short"],
                # The server addresses a map with a byte of bank and a byte of map.
                "bank": entry["id"] >> 8,
                "map": entry["id"] & 0xFF,
                "weather": header.get("weather") if header else None,
                "mapType": header.get("mapType") if header else None,
                "matrixId": header.get("matrixId") if header else None,
                "eventsBank": header.get("eventsBank") if header else None,
                "region": header.get("regionNo") if header else None,
                "zoneEvent": zone,
            }
        )

    body = {
        "game": "HeartGold",
        "gameCode": "IPKE",
        "note": (
            "Joined from include/constants/maps.h, src/data/map_headers.h and the zone_event "
            "filenames. bank/map split the flat HeartGold id into the two bytes the server "
            "addresses a map with; that split is our convention and is unverified against the "
            "retail client. weather and mapType feed NdsMapData; it has a third field, lighting, "
            "which HeartGold has no source for."
        ),
        "maps": maps,
    }
    out.parent.mkdir(parents=True, exist_ok=True)
    out.write_text(json.dumps(body, indent=2) + "\n")

    johto = sum(1 for m in maps if m["region"] == "MAP_REGION_JOHTO")
    kanto = sum(1 for m in maps if m["region"] == "MAP_REGION_KANTO")
    print(
        f"maps={len(maps)} johto={johto} kanto={kanto} "
        f"no-header={missing_header} no-zone-event={missing_zone}"
    )


if __name__ == "__main__":
    main()
