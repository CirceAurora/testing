package dcp.mc.pstp.config

import java.nio.file.Path

object Builder {
    @JvmStatic
    val configDirectory: Path
        get() = throw AssertionError("This method should be overridden")
//
//    init {
//        val gson = GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).setPrettyPrinting().create()
//        val folder = configDirectory.toFile()
//        val file = configDirectory.resolve("projectsavethepets.json").toFile()
//        val authenticationService = YggdrasilAuthenticationService(Proxy.NO_PROXY)
//        val profileRepository = authenticationService.createProfileRepository()
//        val sessionService = authenticationService.createMinecraftSessionService()
//
//        if (!folder.exists() && !folder.mkdirs()) {
//            throw FileNotFoundException("Failed to find and create the configuration directory!")
//        }
//
//
//    }
}