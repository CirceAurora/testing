import java.io.ByteArrayOutputStream
import java.util.Calendar

plugins {
    idea
    id("org.jetbrains.kotlin.jvm") apply false
    id("dev.architectury.loom") apply false
    id("io.github.juuxel.loom-vineflower") apply false
}

val year = Calendar.getInstance().get(Calendar.YEAR)
val gitHash = run {
    ByteArrayOutputStream().use {
        project.exec {
            commandLine = "git rev-parse --verify --short=10 HEAD".split(' ')
            standardOutput = it
        }

        String(it.toByteArray()).trim().padStart(10, '0').take(10)
    }
}

allprojects {
    apply {
        plugin("java")
    }

    configure<BasePluginExtension> {
        archivesName = "${property("archives_name") as String}-$name"
        group = property("group") as String
        version = "${property("mod_version") as String}+$year.$gitHash"
    }

    configure<JavaPluginExtension> {
        withSourcesJar()

        toolchain {
            languageVersion = JavaLanguageVersion.of((property("java_version") as String).toInt())
        }
    }

    tasks {
        withType<JavaCompile>().configureEach {
            options.encoding = "UTF-8"
        }

        named<Jar>("jar") {
            archiveClassifier = "dev"
        }
    }
}

subprojects {
    apply {
        plugin("dev.architectury.loom")
        plugin("io.github.juuxel.loom-vineflower")
        plugin("java-library")
    }

    dependencies {
        "implementation"("org.jetbrains:annotations:22.0.0")
        "mappings"("net.fabricmc:yarn:${property("yarn_mappings") as String}:v2")
        "minecraft"("com.mojang:minecraft:${property("minecraft_version") as String}")
    }

//    if(project.name != "common") {
//        tasks {
//            named<JavaCompile>("javaCompile") {
//                dependsOn(":common:javaCompile")
//            }
//
//            named("build") {
//                dependsOn(":common:build")
//            }
//
//            named<Jar>("jar") {
//                dependsOn(":common:jar")
//            }
//        }
//    }
}

tasks {
    named<Delete>("clean") {
        dependsOn(subprojects.map { it.tasks.named<Delete>("clean") })
    }

    named<Jar>("jar") {
        dependsOn(subprojects.map { it.tasks.named<Jar>("jar") })

        from(rootProject.file("LICENSE")) {
            rename {
                "${it}_${project.extensions.configure<BasePluginExtension>("base") { archivesName }}"
            }
        }

        from(subprojects.map {
            it.extensions.configure<SourceSetContainer>("sourceSets") { named<SourceSet>("main").get() }
        })
    }
}