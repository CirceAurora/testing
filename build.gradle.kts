import java.io.ByteArrayOutputStream
import java.util.Calendar

plugins {
    id("dev.architectury.loom") version "1.2-SNAPSHOT" apply false
    id("io.github.juuxel.loom-vineflower") version "1.11.0" apply false
    id("java")
}

val mod_version: String by project
val year = Calendar.getInstance().get(Calendar.YEAR)
val gitHash = run {
    ByteArrayOutputStream().use {
        project.exec {
            commandLine = "git rev-parse --verify --short=10 HEAD".split(" ")
            standardOutput = it
        }

        String(it.toByteArray()).trim().padStart(10, '0')
    }
}

base {
    version = "$mod_version+$year.$gitHash"
}

allprojects {
    base {
        archivesName = "projectsavethepets"
        group = "dcp.me"
    }

    tasks.withType<JavaCompile> {
        options.encoding = "UTF-8"
        options.release = 19
    }

    tasks.withType<Jar> {
        archiveClassifier = "dev"
    }
}

subprojects {
    base {
        version = "$mod_version+$year.$gitHash.${project.name}"
    }

    apply("dev.architecury.loom")
    apply("io.github.juuxel.loom-vineflower")
    apply("java")

    val yarn_mappings: String by rootProject
    val minecraft_version: String by rootProject

    dependencies {
        implementation("prg.jetbrains:annotations:22.0.0")
//        mapppings("net.fabricmc:yarn:$yarn_mappings:v2")
//        minecraft("com.mojang:minecraft:$minecraft_version")
    }
}

tasks.named("clean") {
    dependsOn(subprojects.map { it.tasks.named("clean") })
}

tasks.withType<Jar> {
    dependsOn(subprojects.map { it.tasks.named("jar") })

    from(project.file("LICENSE")) {
        rename { "${it}_${project.base.archivesName}" }
    }

    from(subprojects.map { it.sourceSets.main.get() })
}