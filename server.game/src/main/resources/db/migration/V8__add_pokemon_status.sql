-- A major status condition outlives the battle it was inflicted in, so it belongs on the monster
-- rather than only in the battle state. StatusCondition enum name, NONE when healthy.
ALTER TABLE pokemon ADD COLUMN status VARCHAR(16) NOT NULL DEFAULT 'NONE';
