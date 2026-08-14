#!/usr/bin/env python3
"""Stitch HeartGold's 32x32 terrain blocks into one collision grid per map.

A matrix is a grid of cells, each cell naming a 32x32 land-data block and the map that cell belongs
to. Several maps share a matrix, which is how the overworld is seamless, so a map's tiles are the
cells whose header equals that map's id rather than the whole matrix.

Matrix format, from src/map_matrix.c:15-60:
    u8 width, u8 height, u8 hasHeaders, u8 hasAltitudes, u8 nameLength, char name[nameLength]
    [u16 headers[w*h]]  [u8 altitudes[w*h]]  u16 models[w*h]
When hasHeaders is 0 every cell belongs to the map that loaded the matrix.

Usage: hg_stitch.py <decomp-dir> <map-table.json> <out.json> [--sample NAME]
"""

from __future__ import annotations

import argparse
import json
import pathlib
import re
import struct
import sys

TILES = 32
TERRAIN_OFFSET = 0x14
TERRAIN_SIZE = 0x800
COLLISION_MASK = 0x8000
BEHAVIOR_MASK = 0x00FF
MATRIX_RE = re.compile(r"NARC_map_matrix_map_matrix_(\d+)")


def read_narc(path: pathlib.Path) -> list[bytes]:
    blob = path.read_bytes()
    if blob[:4] != b"NARC":
        sys.exit(f"{path} is not a NARC")
    header_size, _ = struct.unpack_from("<HH", blob, 12)
    offset = header_size
    fatb_size, count = struct.unpack_from("<II", blob, offset + 4)
    entries = [struct.unpack_from("<II", blob, offset + 12 + i * 8) for i in range(count)]
    offset += fatb_size
    (fntb_size,) = struct.unpack_from("<I", blob, offset + 4)
    offset += fntb_size
    image = offset + 8
    return [blob[image + s : image + e] for s, e in entries]


def read_matrix(path: pathlib.Path):
    blob = path.read_bytes()
    width, height, has_headers, has_altitudes, name_length = struct.unpack_from("<5B", blob, 0)
    cursor = 5 + name_length
    cells = width * height
    headers = None
    if has_headers:
        headers = list(struct.unpack_from(f"<{cells}H", blob, cursor))
        cursor += cells * 2
    if has_altitudes:
        cursor += cells
    models = list(struct.unpack_from(f"<{cells}H", blob, cursor))
    return width, height, headers, models


def block_grid(member: bytes):
    if len(member) < TERRAIN_OFFSET + TERRAIN_SIZE:
        return None
    return struct.unpack_from(f"<{TILES * TILES}H", member, TERRAIN_OFFSET)


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("decomp")
    parser.add_argument("map_table")
    parser.add_argument("out")
    parser.add_argument("--sample")
    args = parser.parse_args()

    decomp = pathlib.Path(args.decomp)
    blocks = read_narc(decomp / "files" / "a" / "0" / "6" / "5")
    matrix_dir = decomp / "fielddata" / "mapmatrix" / "map_matrix"
    if not matrix_dir.is_dir():
        matrix_dir = decomp / "files" / "fielddata" / "mapmatrix" / "map_matrix"
    matrices = {}
    for path in matrix_dir.glob("map_matrix_*.bin"):
        number = int(path.name.split("_")[2].split(".")[0])
        matrices[number] = path

    table = json.loads(pathlib.Path(args.map_table).read_text())["maps"]

    results = []
    sample_dump = None
    for entry in table:
        symbol = entry.get("matrixId") or ""
        match = MATRIX_RE.search(symbol)
        if not match:
            continue
        matrix_id = int(match.group(1))
        path = matrices.get(matrix_id)
        if path is None:
            continue
        width, height, headers, models = read_matrix(path)

        # The cells belonging to this map. With no header section the matrix is this map alone.
        wanted = []
        for index in range(width * height):
            if headers is None or headers[index] == entry["id"]:
                wanted.append(index)
        if not wanted:
            continue

        xs = [i % width for i in wanted]
        zs = [i // width for i in wanted]
        min_x, max_x, min_z, max_z = min(xs), max(xs), min(zs), max(zs)
        cells_w = max_x - min_x + 1
        cells_h = max_z - min_z + 1

        tiles = cells_w * TILES * cells_h * TILES
        blocked = 0
        encounters = 0
        missing_blocks = 0
        behaviours = {}
        grid = None
        if args.sample and entry["name"] == args.sample:
            grid = [[None] * (cells_w * TILES) for _ in range(cells_h * TILES)]

        for index in wanted:
            cell_x = index % width - min_x
            cell_z = index // width - min_z
            model = models[index]
            words = block_grid(blocks[model]) if model < len(blocks) else None
            if words is None:
                missing_blocks += 1
                continue
            for i, word in enumerate(words):
                behaviour = word & BEHAVIOR_MASK
                is_blocked = bool(word & COLLISION_MASK)
                blocked += is_blocked
                behaviours[behaviour] = behaviours.get(behaviour, 0) + 1
                if grid is not None:
                    grid[cell_z * TILES + i // TILES][cell_x * TILES + i % TILES] = (
                        "#" if is_blocked else "."
                    )

        results.append(
            {
                "id": entry["id"],
                "name": entry["name"],
                "short": entry["short"],
                "region": entry["region"],
                "matrixId": matrix_id,
                "cells": len(wanted),
                "widthTiles": cells_w * TILES,
                "heightTiles": cells_h * TILES,
                "tiles": tiles,
                "blockedTiles": blocked,
                "missingBlocks": missing_blocks,
                "topBehaviors": sorted(behaviours.items(), key=lambda kv: -kv[1])[:5],
            }
        )
        if grid is not None:
            sample_dump = grid

    out = pathlib.Path(args.out)
    out.parent.mkdir(parents=True, exist_ok=True)
    out.write_text(json.dumps({"maps": results}, indent=2) + "\n")

    johto = [r for r in results if r["region"] == "MAP_REGION_JOHTO"]
    missing = sum(r["missingBlocks"] for r in results)
    print(
        f"stitched={len(results)} johto={len(johto)} missing-blocks={missing} "
        f"largest={max((r['tiles'] for r in results), default=0)} tiles"
    )
    if sample_dump:
        print(f"--- {args.sample} ---")
        for row in sample_dump[:24]:
            print("".join(c or " " for c in row[:72]))


if __name__ == "__main__":
    main()
