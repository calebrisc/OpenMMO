import org.gradle.language.jvm.tasks.ProcessResources

plugins {
  application
  id("buildsrc.convention.kotlin-jvm")
  id("buildsrc.convention.spotless")
  id("buildsrc.convention.sonarlint")
  id("buildsrc.common.keys")
}

application {
  mainClass.set("de.fiereu.openmmo.patcher.Launcher")
  applicationName = "openmmo-patcher"
}

dependencies {
  implementation(libs.bundles.crypto)
  testImplementation(libs.bundles.kotest)
}

fun String.evalEnvVars(): String =
    replace(Regex("\\$\\{([^}]+)\\}")) { System.getenv(it.groupValues[1]) ?: it.value }

tasks.named<JavaExec>("run") {
  group = "application"
  description = "Patches the PokeMMO client and runs the patched copy"

  val pokemmoExecutable = (project.findProperty("pokemmo.executable") as String).evalEnvVars()
  val pokemmoWorkingDir = (project.findProperty("pokemmo.workingDir") as String).evalEnvVars()
  val patchedExecutable = layout.buildDirectory.file("PokeMMO-openmmo.exe")

  systemProperty("openmmo.executable", pokemmoExecutable)
  systemProperty("openmmo.workingDir", pokemmoWorkingDir)
  systemProperty("openmmo.output", patchedExecutable.get().asFile.path)
  maxHeapSize = "1g"
}

tasks.named<ProcessResources>("processResources") {
  dependsOn(":keys:generateGame", ":keys:generateChat", ":keys:generateFeed")
  from(project(":keys").layout.buildDirectory) {
    include("game.public.pem", "chat.public.pem", "feed.public.pem", "feed.private.pem")
    rename("(.*)\\.pem", "$1.key")
  }
}
