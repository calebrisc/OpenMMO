package de.fiereu.openmmo.server.game.storage

import de.fiereu.openmmo.common.enums.GuildPermission
import de.fiereu.openmmo.common.enums.GuildRank
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class GuildStoreTest :
    FunSpec({
      test("creating a guild registers the leader as a member and binds the lookup") {
        val store = GuildStore(FakeGuildRepository())
        store.getGuildForChar(100L) shouldBe null

        val guild = store.createGuild("Knights", "KNT", leaderId = 100L, leaderName = "Leader")
        guild.name shouldBe "Knights"
        guild.tag shouldBe "KNT"
        guild.members.map { it.id } shouldBe listOf(100L)
        guild.members.single().rank shouldBe GuildRank.BOSS
        store.getGuildForChar(100L) shouldBe guild
      }

      test("invited members append to the roster and bind their own lookup") {
        val store = GuildStore(FakeGuildRepository())
        val guild = store.createGuild("Knights", "KNT", leaderId = 100L, leaderName = "Leader")
        store.addMember(guild, GuildMember(200L, "Grunt", GuildRank.GRUNT, leader = false))
        guild.members.map { it.name } shouldBe listOf("Leader", "Grunt")
        store.getGuildForChar(200L) shouldBe guild
      }

      test("rank assign updates a member and kick removes them") {
        val store = GuildStore(FakeGuildRepository())
        val guild = store.createGuild("Knights", "KNT", leaderId = 100L, leaderName = "Leader")
        store.addMember(guild, GuildMember(200L, "Recruit", GuildRank.GRUNT, leader = false))

        store.setMemberRank(guild, 200L, GuildRank.OFFICER)
        guild.members.single { it.id == 200L }.rank shouldBe GuildRank.OFFICER

        store.removeMember(guild, 200L)
        guild.members.map { it.id } shouldBe listOf(100L)
        store.getGuildForChar(200L) shouldBe null
      }

      test("transferring leadership promotes the target and demotes the old Boss to Executive") {
        val store = GuildStore(FakeGuildRepository())
        val guild = store.createGuild("Knights", "KNT", leaderId = 100L, leaderName = "Leader")
        store.addMember(guild, GuildMember(200L, "Heir", GuildRank.OFFICER, leader = false))

        store.transferLeadership(guild, 200L)

        val byId = guild.members.associateBy { it.id }
        byId.getValue(200L).rank shouldBe GuildRank.BOSS
        byId.getValue(200L).leader shouldBe true
        byId.getValue(100L).rank shouldBe GuildRank.EXECUTIVE
        byId.getValue(100L).leader shouldBe false
      }

      test("leadership does not transfer to somebody who is not a member") {
        val store = GuildStore(FakeGuildRepository())
        val guild = store.createGuild("Knights", "KNT", leaderId = 100L, leaderName = "Leader")

        store.transferLeadership(guild, 999L)

        guild.members.single().id shouldBe 100L
        guild.members.single().leader shouldBe true
      }

      test("leave unbinds the leaver and disband removes the guild") {
        val store = GuildStore(FakeGuildRepository())
        store.createGuild("Knights", "KNT", leaderId = 100L, leaderName = "Leader")
        store.leaveGuild(100L)
        store.getGuildForChar(100L) shouldBe null

        store.createGuild("Knights", "KNT", leaderId = 100L, leaderName = "Leader")
        store.disbandGuild(100L)
        store.getGuildForChar(100L) shouldBe null
      }

      test("a guild and its roster survive a restart") {
        val repository = FakeGuildRepository()
        val before = GuildStore(repository)
        val guild = before.createGuild("Knights", "KNT", leaderId = 100L, leaderName = "Leader")
        before.addMember(guild, GuildMember(200L, "Grunt", GuildRank.GRUNT, leader = false))
        before.setMotd(guild, "raid at eight")
        before.setPermissions(guild, mapOf(GuildRank.OFFICER to setOf(GuildPermission.INVITE)))

        val after = GuildStore(repository)
        after.loadAll()

        val reloaded = after.getGuildForChar(100L)
        reloaded shouldNotBe null
        reloaded!!.name shouldBe "Knights"
        reloaded.motd shouldBe "raid at eight"
        reloaded.members.map { it.name } shouldBe listOf("Leader", "Grunt")
        reloaded.permissions shouldBe mapOf(GuildRank.OFFICER to setOf(GuildPermission.INVITE))
        // The invited member's lookup has to be rebuilt too, not just the founder's.
        after.getGuildForChar(200L) shouldBe reloaded
      }

      test("ids issued after a restart do not collide with the ones already stored") {
        val repository = FakeGuildRepository()
        val before = GuildStore(repository)
        val first = before.createGuild("Knights", "KNT", leaderId = 100L, leaderName = "Leader")

        val after = GuildStore(repository)
        after.loadAll()
        val second = after.createGuild("Rogues", "RGE", leaderId = 300L, leaderName = "Other")

        second.id shouldNotBe first.id
      }

      test("a disbanded guild does not come back after a restart") {
        val repository = FakeGuildRepository()
        val before = GuildStore(repository)
        before.createGuild("Knights", "KNT", leaderId = 100L, leaderName = "Leader")
        before.disbandGuild(100L)

        val after = GuildStore(repository)
        after.loadAll()

        after.getGuildForChar(100L) shouldBe null
      }
    })
