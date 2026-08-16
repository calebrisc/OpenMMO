package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.Pokemon
import de.fiereu.openmmo.common.PokemonMove
import de.fiereu.openmmo.common.enums.EVs
import de.fiereu.openmmo.common.enums.IVs
import de.fiereu.openmmo.common.enums.PokemonContainer
import de.fiereu.openmmo.common.enums.StatusCondition
import de.fiereu.openmmo.db.game.tables.records.PokemonRecord

/**
 * Reading a monster row back.
 *
 * It sits apart from the character repository because a monster is not only ever read as part of
 * its owner: one on the market has left its owner's hands and is read on its own.
 */
internal fun PokemonRecord.toPokemon(): Pokemon =
    Pokemon(
        id = id,
        ownerId = ownerId,
        container = PokemonContainer.valueOf(container),
        containerSlot = containerSlot,
        dexId = dexId,
        seed = seed,
        ot = ot,
        nickname = nickname ?: "",
        level = pokemonLevel.toByte(),
        hp = hp,
        xp = xp,
        eVs = hydrateEvs(),
        iVs = hydrateIvs(),
        moves =
            listOf(
                PokemonMove(move1Id ?: 0, (move1Pp ?: 0).toByte()),
                PokemonMove(move2Id ?: 0, (move2Pp ?: 0).toByte()),
                PokemonMove(move3Id ?: 0, (move3Pp ?: 0).toByte()),
                PokemonMove(move4Id ?: 0, (move4Pp ?: 0).toByte()),
            ),
        isShiny = isShiny ?: false,
        hasHiddenAbility = hasHiddenAbility ?: false,
        isAlpha = isAlpha ?: false,
        isSecret = isSecret ?: false,
        isFatefulEncounter = isFatefulEncounter ?: false,
        isRaidEncounter = isRaidEncounter ?: false,
        isEgg = isEgg ?: false,
        caughtAt = caughtAt,
        status = StatusCondition.parse(status),
    )

private fun PokemonRecord.hydrateEvs(): EVs =
    EVs().also {
      it.hp = (evHp ?: 0).toInt()
      it.atk = (evAtk ?: 0).toInt()
      it.def = (evDef ?: 0).toInt()
      it.spAtk = (evSpAtk ?: 0).toInt()
      it.spDef = (evSpDef ?: 0).toInt()
      it.spd = (evSpd ?: 0).toInt()
    }

private fun PokemonRecord.hydrateIvs(): IVs =
    IVs().also {
      it.hp = (ivHp ?: 0).toInt()
      it.atk = (ivAtk ?: 0).toInt()
      it.def = (ivDef ?: 0).toInt()
      it.spAtk = (ivSpAtk ?: 0).toInt()
      it.spDef = (ivSpDef ?: 0).toInt()
      it.spd = (ivSpd ?: 0).toInt()
    }
