package de.fiereu.openmmo.server.game.storage

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

private fun member(id: Long) = LinkMember(id, "Player$id")

class LinkStoreTest :
    FunSpec({
      test("creating a link twice returns the one that already exists") {
        val store = LinkStore()
        val first = store.create(member(1))
        val second = store.create(member(1))
        second shouldBe first
        first.members.size shouldBe 1
      }

      test("the first member is the leader") {
        val store = LinkStore()
        val link = store.create(member(1))
        store.add(link, member(2)).shouldBeTrue()
        link.leader.charId shouldBe 1L
      }

      test("a link holds four players and refuses a fifth") {
        val store = LinkStore()
        val link = store.create(member(1))
        store.add(link, member(2)).shouldBeTrue()
        store.add(link, member(3)).shouldBeTrue()
        store.add(link, member(4)).shouldBeTrue()
        link.full.shouldBeTrue()
        store.add(link, member(5)).shouldBeFalse()
        link.members.size shouldBe MAX_LINK_SIZE
      }

      test("a player cannot be in two links at once") {
        val store = LinkStore()
        val first = store.create(member(1))
        store.add(first, member(2)).shouldBeTrue()

        val second = store.create(member(3))
        store.add(second, member(2)).shouldBeFalse()

        store.forChar(2L) shouldBe first
      }

      test("leaving a link of three leaves the other two in it") {
        val store = LinkStore()
        val link = store.create(member(1))
        store.add(link, member(2))
        store.add(link, member(3))

        val remaining = store.remove(2L)!!

        remaining.members.map { it.charId } shouldBe listOf(1L, 3L)
        store.forChar(2L) shouldBe null
        store.forChar(3L) shouldBe remaining
      }

      test("dropping to one player breaks the link up for everyone") {
        val store = LinkStore()
        val link = store.create(member(1))
        store.add(link, member(2))

        val remaining = store.remove(2L)!!

        // The survivor is reported so the caller can tell them, but nobody is left in a link.
        remaining.members.map { it.charId } shouldBe listOf(1L)
        store.forChar(1L) shouldBe null
        store.forChar(2L) shouldBe null
      }

      test("the leader passes to the next member when the leader leaves") {
        val store = LinkStore()
        val link = store.create(member(1))
        store.add(link, member(2))
        store.add(link, member(3))

        val remaining = store.remove(1L)!!

        remaining.leader.charId shouldBe 2L
      }

      test("removing somebody who is not in a link reports nothing") {
        LinkStore().remove(99L) shouldBe null
      }
    })
