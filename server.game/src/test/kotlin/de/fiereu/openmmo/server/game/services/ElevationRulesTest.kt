package de.fiereu.openmmo.server.game.services

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

/**
 * The games' own rules, from IsElevationMismatchAt and ObjectEventUpdateElevation. Without them a
 * cave is one flat sheet and a player walks between its levels wherever the geometry touches.
 */
class ElevationRulesTest :
    FunSpec({
      test("a player stays on the level they are standing on") {
        elevationAllows(playerElevation = 3, tileElevation = 3) shouldBe true
        elevationAllows(playerElevation = 3, tileElevation = 1) shouldBe false
      }

      test("a transition tile can be entered from any level, and left onto any") {
        elevationAllows(playerElevation = 3, tileElevation = 0) shouldBe true
        elevationAllows(playerElevation = 0, tileElevation = 1) shouldBe true
      }

      test("the any elevation blocks nothing") {
        elevationAllows(playerElevation = 3, tileElevation = ELEVATION_ANY) shouldBe true
      }

      test("stepping onto a level puts the player on it") {
        elevationAfterStep(current = 0, fromElevation = 0, toElevation = 3) shouldBe 3
      }

      test("stepping onto a transition tile leaves the player on it, which is how a slope works") {
        // Zero is taken, not ignored: it is what lets the next step reach a different level.
        elevationAfterStep(current = 3, fromElevation = 3, toElevation = 0) shouldBe 0
      }

      test("either end being the any elevation leaves the player where they were") {
        elevationAfterStep(current = 3, fromElevation = 3, toElevation = ELEVATION_ANY) shouldBe 3
        elevationAfterStep(current = 3, fromElevation = ELEVATION_ANY, toElevation = 1) shouldBe 3
      }
    })
