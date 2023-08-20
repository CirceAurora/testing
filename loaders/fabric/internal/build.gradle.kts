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
        val base = file("base.fabric.mod.json")
        val file = Path.of(this.temporaryDir.absolutePath, "fabric.mod.json").toFile().apply { createNewFile() }

        fun JsonObject.obj(name: String, block: JsonObject.() -> Unit) = block(if (has(name)) getAsJsonObject(name) else JsonObject())
        fun JsonObject.arr(name: String, block: JsonArray.() -> Unit) = block(if (has(name)) getAsJsonArray(name) else JsonArray())
        fun JsonObject.str(name: String, value: String) = if (value.isNotBlank()) add(name, JsonPrimitive(value)) else Unit
        fun JsonObject.prop(name: String, property: String, modify: (String) -> String) = add(
            name,
            JsonPrimitive(modify(rootProject.property(property) as String))
        )
        fun JsonObject.prop(name: String, property: String) = prop(name, property) { it }

        FileWriter(file).use { writer ->
            FileReader(base).use { reader ->
                GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create().toJson(JsonParser.parseReader(reader).asJsonObject.apply {
                    prop("id", "name")
                    prop("name", "mod_name")
                    str("version", project.version as String)
                    prop("description", "description")
                    prop("icon", "icon")
                    prop("license", "license")
                    prop("environment", "environment")
                    obj("depends") {
                        prop("fabricloader", "fabric_version") { ">=$it" }
                        prop("fabric-key-binding-api-v1", "fabric_api_version") { ">=$it" }
                        prop("minecraft", "minecraft_version") { ">=$it" }
                    }
                    arr("authors") {
                        (rootProject.property("authors") as String).split('|').forEach {
                            it.split(',').forEach { contact ->
                                val (name, value) = contact.split(':', limit = 2)
                                str(name, value)
                            }
                        }
                    }
                    obj("contact") {
                        prop("homepage", "website")
                        prop("curseforge", "curseforge")
                        prop("modrinth", "modrinth")
                        prop("sources", "sources")
                        prop("issues", "issues")
                        str("discord", rootProject.property("discord") as String)
                    }
                }, writer)
            }
        }

        from(file)
    }
}