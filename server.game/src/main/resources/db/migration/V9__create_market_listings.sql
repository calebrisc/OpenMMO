-- Monsters offered for sale, and who is owed the money when one sells.
--
-- The monster itself stays in the pokemon table and moves to the GTS container, which takes it out
-- of the seller's party and boxes without copying it anywhere. A listing is only the asking price
-- and who set it, so a monster can never exist in two places at once and a lost listing row leaves
-- a monster that is still owned rather than one that is gone.
CREATE TABLE market_listing (
  id           BIGSERIAL   PRIMARY KEY,
  pokemon_id   BIGINT      NOT NULL UNIQUE REFERENCES pokemon (id) ON DELETE CASCADE,
  seller_id    BIGINT      NOT NULL REFERENCES characters (id) ON DELETE CASCADE,
  -- Kept alongside the id so a listing can be shown without loading the seller.
  seller_name  VARCHAR(32) NOT NULL,
  price        INT         NOT NULL CHECK (price > 0),
  -- The species, so browsing does not have to read every listed monster.
  dex_id       INT         NOT NULL,
  listed_at    TIMESTAMP   NOT NULL
);

CREATE INDEX idx_market_listing_seller ON market_listing (seller_id);
CREATE INDEX idx_market_listing_dex ON market_listing (dex_id);
