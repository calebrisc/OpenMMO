package de.fiereu.openmmo.pokemon

import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

class EvolutionTableTest :
    FunSpec({
      test("the table carries the level evolutions the decomp defines") {
        EvolutionTable.size shouldBe 134
      }

      test("a starter evolves at the level the games use") {
        // Bulbasaur into Ivysaur at 16, and Ivysaur into Venusaur at 32.
        EvolutionTable.at(1, 16) shouldBe LevelEvolution(16, 2)
        EvolutionTable.at(2, 32) shouldBe LevelEvolution(32, 3)
      }

      test("a monster below the threshold is left alone") {
        EvolutionTable.at(1, 15).shouldBeNull()
      }

      test("levelling past the threshold still evolves") {
        // Several levels can arrive at once from one battle, so the check cannot be an equality.
        EvolutionTable.at(1, 25) shouldBe LevelEvolution(16, 2)
      }

      test("a species that never evolves has no entry") {
        EvolutionTable.has(132) shouldBe false // Ditto
        EvolutionTable.at(151, 100).shouldBeNull() // Mew
      }

      test("hoenn species are carried across, not just kanto") {
        // Treecko is national 252, and its internal decomp id is 277, so a wrong mapping shows here.
        EvolutionTable.at(252, 16) shouldBe LevelEvolution(16, 253)
        EvolutionTable.size shouldBeGreaterThan 130
      }
    })
