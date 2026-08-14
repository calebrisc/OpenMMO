-- Guilds and friend lists used to live in plain maps, so founding a guild charged the player
-- 15000 durably and then lost the guild on the next restart.

CREATE TABLE guilds (
  id         BIGINT       NOT NULL PRIMARY KEY,
  name       VARCHAR(64)  NOT NULL,
  tag        VARCHAR(16)  NOT NULL,
  motd       VARCHAR(256) NOT NULL,
  founded_at TIMESTAMP    NOT NULL,
  updated_at TIMESTAMP    NOT NULL
);

CREATE TABLE guild_members (
  guild_id     BIGINT      NOT NULL REFERENCES guilds (id) ON DELETE CASCADE,
  character_id BIGINT      NOT NULL REFERENCES characters (id) ON DELETE CASCADE,
  name         VARCHAR(32) NOT NULL,
  -- GuildRank enum name. Renaming an enum entry breaks stored data.
  rank         VARCHAR(16) NOT NULL,
  leader       BOOLEAN     NOT NULL,
  joined_at    TIMESTAMP   NOT NULL,
  PRIMARY KEY (guild_id, character_id)
);

-- A character belongs to at most one guild, which is what makes the char to guild lookup total.
CREATE UNIQUE INDEX uq_guild_members_character ON guild_members (character_id);

CREATE TABLE guild_permissions (
  guild_id   BIGINT      NOT NULL REFERENCES guilds (id) ON DELETE CASCADE,
  -- GuildRank enum name.
  rank       VARCHAR(16) NOT NULL,
  -- GuildPermission enum name.
  permission VARCHAR(32) NOT NULL,
  PRIMARY KEY (guild_id, rank, permission)
);

CREATE TABLE guild_rank_labels (
  guild_id BIGINT      NOT NULL REFERENCES guilds (id) ON DELETE CASCADE,
  rank     VARCHAR(16) NOT NULL,
  label    VARCHAR(32) NOT NULL,
  PRIMARY KEY (guild_id, rank)
);

-- seq is assigned per guild by GuildStore rather than by a sequence, so the ordering the client
-- pages through is the ordering we wrote.
CREATE TABLE guild_log (
  guild_id   BIGINT      NOT NULL REFERENCES guilds (id) ON DELETE CASCADE,
  seq        INT         NOT NULL,
  -- GuildActivityType enum name.
  type       VARCHAR(16) NOT NULL,
  actor      VARCHAR(32) NOT NULL,
  target     VARCHAR(32) NOT NULL,
  created_at INT         NOT NULL,
  PRIMARY KEY (guild_id, seq)
);

-- Friends and blocks are per account, not per character, which is how the client asks for them.
CREATE TABLE account_friends (
  user_id  INT         NOT NULL,
  name     VARCHAR(32) NOT NULL,
  added_at TIMESTAMP   NOT NULL,
  PRIMARY KEY (user_id, name)
);

CREATE TABLE account_blocks (
  user_id    INT          NOT NULL,
  name       VARCHAR(32)  NOT NULL,
  reason     VARCHAR(256) NOT NULL,
  blocked_at TIMESTAMP    NOT NULL,
  PRIMARY KEY (user_id, name)
);
