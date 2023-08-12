import java.io.FileReader
import java.io.FileWriter
import java.nio.file.Path
import com.google.gson.GsonBuilder
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.JsonPrimitive

dependencies {
    modImplementation("net.fabricmc:fabric-loader:${property("fabric_version") as String}")
    modImplementation(fabricApi.module("fabric-key-binding-api-v1", property("fabric_api_version") as String))
}

tasks {
    runClient {
        dependsOn(project.tasks.processResources)
    }

    processResources {
        val base = file("fabric.mod.json.base")
        val file = Path.of(this.temporaryDir.absolutePath, "fabric.mod.json").toFile().apply { createNewFile() }

        fun JsonObject.add(name: String, property: String) = add(name, JsonPrimitive(rootProject.property(property) as String))
        fun JsonObject.add(name: String, prepend: String, property: String) = add(name, JsonPrimitive(prepend + rootProject.property(property) as String))
        fun JsonObject.include(name: String, value: String): Unit = if (value.isNotBlank()) add(name, JsonPrimitive(value)) else Unit

        FileWriter(file).use { writer ->
            FileReader(base).use { reader ->
                GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(JsonParser.parseReader(reader).asJsonObject.apply {
                    add("id", "archives_name")
                    add("name", "mod_name")
                    add("version", JsonPrimitive(project.version as String))
                    add("description", "description")
                    add("icon", "icon")
                    add("license", "license")
                    add("environment", "environment")
                    add("depends", JsonObject().apply {
                        add("fabricloader", ">=", "fabric_version")
                        add("fabric-key-binding-api-v1", ">=", "fabric_api_version")
                        add("minecraft", ">=", "minecraft_version")
                    })
                    add("authors", JsonArray().apply {
                        (rootProject.property("authors") as String).split(',').forEach {
                            val (name, website, email, discord, extras) = it.split(':')

                            add(JsonObject().apply {
                                add("name", JsonPrimitive(name))
                                add("contacts", JsonObject().apply {

                                    include("homepage", website)
                                    include("email", email)
                                    include("discord_server", discord)

                                    if (extras.isNotBlank()) {
                                        extras.split('|').forEach { s ->
                                            val (media, contact) = s.split('@', limit = 2)
                                            include(media, contact)
                                        }
                                    }
                                })
                            })
                        }
                    })
                    add("contact", JsonObject().apply {
                        add("homepage", "website")
                        add("curseforge", "curseforge")
                        add("modrinth", "modrinth")
                        add("sources", "sources")
                        add("issues", "issues")
                        include("discord", rootProject.property("discord") as String)
                    })
                }, writer)
            }
        }

//        from(file)
    }
}