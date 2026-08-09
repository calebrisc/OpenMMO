# Deployment

The production deployment consists of two independent Compose stacks:

- `login.compose.yml` runs the login server and its PostgreSQL database.
- `game.compose.yml` runs the game server and its PostgreSQL database.

Only TCP ports `2106` and `7777` are published. Both databases remain private
to their respective Compose networks.

## Configuration

Copy `.env.example` to a private location and replace every example value. Both
stacks must receive the same session secret and private game key.

Generate the key pair with:

```bash
./gradlew :keys:generateGame
```

The private key is written to `keys/build/game.private.pem`. Keep it outside the
repository on the server. Patch clients with the matching public key.

`GAME_SERVER_PUBLIC_IPV4` must be the VPS public IPv4 address. The login server
sends this address to clients when they select the game server.

Set `GAME_SERVER_PUBLIC_IPV6` to the VPS public IPv6 address. Without public
IPv6, use expanded mapped form `::ffff:cb00:710a` for `203.0.113.10`.

`OPENMMO_CUSTOM_MAPS_PATH` reserves a host folder for custom map files. Runtime
custom-map loading is not implemented yet, so the server currently ignores it.

## Manual deployment

From this directory, deploy both stacks using the same environment file:

```bash
docker compose --env-file .env -f game.compose.yml up -d
docker compose --env-file .env -f login.compose.yml up -d
```

Point `login.openmmo.dev` at the VPS public IP. DNS provides the address while
the patcher supplies login port `2106`. The login server supplies game port
`7777` itself.

Allow inbound TCP `2106` and `7777` in the provider and Windows firewalls. Do
not expose PostgreSQL ports `20011`, `20021`, or `5432`.

## Komodo

Create two Komodo Stack resources from this repository:

| Stack | Compose file |
| --- | --- |
| `openmmo-login` | `deploy/login.compose.yml` |
| `openmmo-game` | `deploy/game.compose.yml` |

Add the environment values through Komodo and enable `auto_update` for both
stacks. Release images carry both versioned and `latest` tags, so Komodo can
redeploy automatically when the `latest` digest changes.

Make both GHCR packages public, or configure Komodo with a read-only package
token before the first deployment.
