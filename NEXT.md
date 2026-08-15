# What the packet archive settled, and what it did not

Captures come from ByteDex (<https://bytedex.fiereu.de>), a community archive of real PokeMMO
traffic. Query it by opcode rather than browsing: `/api/v1/packets?packetId=<decimal>&limit=60`
lists shapes, `/api/v1/packets/{id}` returns the payload as base64. Run those from a signed-in
browser tab; the session cookie does the authenticating.

Everything below replaced a guess. Four of tonight's failures were the same failure: no capture
existed, so the shape was reasoned about instead of read.

## Settled

### 1. The switch that crashes the client — opcode 0x35

45 captures, three shapes: **65 bytes (22), 55 (19), 25 (4)**. Only the last two were known, and
neither is the player's own first switch.

The 65-byte form, saved as `s2c/35/player_first_switch_in_{a,b}.bin`:

| byte | value | meaning |
| --- | --- | --- |
| 0 | `00` | side, always the player's own |
| 1 | `00` | reserved |
| 2 | `01` | a full description follows |
| 3 | `00` | |
| 4 | varies | **the party position** |

Across eight samples byte 2 is always `1` and byte 3 always `0`, while byte 4 ranges over 1–5 and
tracks the species. So the two bytes the codec calls `newSlot` and `oldSlot` are not party
positions at all: byte 2 says whether a full description follows, which is why the 25-byte return
form carries `0` there and both full-block forms carry `1`. The party position lives only inside
the block.

The server writes the party position into byte 2. Switching to the fifth monster put a `4` where
the client only ever sees a `1`, and it died. Three attempts at this failed because all three
argued about which description to send rather than where the position goes.

**Fix**: player switch-ins send the full block, byte 2 = 1, byte 3 = 0, party position in the
block's slot. Then revert the field-state workaround in `BattleService.performSwitch`, which was
only ever a way of avoiding a packet whose shape was unknown.

### 2. Evolution — opcode 0x0B, 4 bytes, server to client

50 captures. `02 20 00 ff` and `01 20 00 ff` (`s2c/0b/evolution_prompt_{a,b}.bin`). The protocol
registers this opcode client-to-server only, so the prompt the server is supposed to send has no
definition at all, which is why nothing on this server has ever evolved. The differing first byte
is the obvious candidate for which monster.

### 3. Two monsters on one side — opcodes 0x40 and 0x42

`BattleSideAddPokemon` has 15 captures (15 and 16 bytes) and `BattleSideParty` 24, one of which
holds three monsters in 46 bytes (`s2c/40/side_party_three.bin`). Both were tried blind and
produced nothing visible. Now there are working examples to compare against, which is the
prerequisite for co-op battles and for raids showing teammates.

### 4. Shapes worth checking before touching anything else

`LocalPlayerState` 0xF3 has 24 captures (411 and 465 bytes) and carries the two dex lists, which is
why the Pokédex shows Unova entries. `BattleFieldState` 0x30 has 50, from 204 to 411 bytes.
`GtlSearchPage` 0x9B has 50, the server side running to ~1800 bytes, enough to drive the client's
real market screens instead of chat. `PcBoxStore` 0x79 and `Movement` 0x06 match our codecs
exactly, so those are confirmed rather than assumed.

## Not settled, and the archive cannot help

**Zero captures**: matchmaking join 0x49, queue position 0xF0, trade action / duel invite 0x50,
duel outcome 0x51, trade select 0x52, battle start scene 0xCA.

Nobody recorded a session that used them. Matchmaking, trading through the client's own window, and
duel invitations stay unknown, and no amount of querying changes that. They keep their chat-driven
commands until somebody captures a session that exercises them.

## Order of work

1. The switch, from the fixtures above. It is a crash, it is understood, and the fixtures make it
   testable.
2. Evolution. Nothing on this server has ever evolved; it affects every hour already played.
3. The dex lists, from the 0xF3 captures.
4. Two monsters on a side, from 0x40 and 0x42. Co-op battles and visible raid teammates both wait
   on it.
5. The market screens, from the 0x9B captures, replacing the chat commands.

Nothing here has been seen by two players at once. Duels, trading, PC boxes, raids, tournaments and
the market are all verified only from the server's side.
