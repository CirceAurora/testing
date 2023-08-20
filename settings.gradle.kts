@file:Suppress("LocalVariableName")

val name: String by settings
rootProject.name = name

pluginManagement {
    repositories {
        maven("https://maven.fabricmc.net/") { name = "Fabric" }
        maven("https://maven.architectury.dev/") { name = "Architectury" }
        maven("https://maven.minecraftforge.net/") { name = "Forge" }
        mavenCentral()
        gradlePluginPortal()
    }

    val architectury_loom_version: String by settings
    val loom_vineflower_version: String by settings

    plugins {
        id("dev.architectury.loom") version architectury_loom_version
        id("io.github.juuxel.loom-vineflower") version loom_vineflower_version
    }
}

include("common:public", "common:internal", "loaders:fabric:internal")