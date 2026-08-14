package de.fiereu.openmmo.server.game.storage

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class SocialStoreTest :
    FunSpec({
      test("a new account starts with nobody on its friend list") {
        val store = SocialStore(FakeSocialRepository())
        store.getFriends(1) shouldBe emptySet()
      }

      test("friends survive add and remove") {
        val store = SocialStore(FakeSocialRepository())
        store.addFriend(1, "Yellow")
        store.getFriends(1).contains("Yellow") shouldBe true

        store.removeFriend(1, "Yellow") shouldBe true
        store.getFriends(1).contains("Yellow") shouldBe false
        store.removeFriend(1, "Yellow") shouldBe false
      }

      test("block list is independent per user") {
        val store = SocialStore(FakeSocialRepository())
        store.getBlocked(1) shouldBe emptySet()

        store.block(1, "Troll", "spam")
        store.getBlocked(1) shouldBe setOf("Troll")
        store.getBlocked(2) shouldBe emptySet()

        store.unblock(1, "Troll") shouldBe true
        store.getBlocked(1) shouldBe emptySet()
      }

      test("friends and blocks survive a restart") {
        val repository = FakeSocialRepository()
        val before = SocialStore(repository)
        before.addFriend(1, "Yellow")
        before.block(1, "Troll", "spam")

        val after = SocialStore(repository)
        after.getFriends(1) shouldBe setOf("Yellow")
        after.getBlocked(1) shouldBe setOf("Troll")
      }

      test("the cached block view is empty until it is warmed, then it answers chat") {
        val store = SocialStore(FakeSocialRepository())
        store.block(1, "Troll", "spam")

        val fresh = SocialStore(FakeSocialRepository())
        fresh.blockedCached(1) shouldBe emptySet()

        store.blockedCached(1) shouldBe setOf("Troll")
      }
    })
