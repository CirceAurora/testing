package dcp.mc.pstp.config;

import java.io.IOError;
import java.io.IOException;
import java.net.Proxy;

import dcp.mc.pstp.ModLoaderThings;
import dcp.mc.pstp.Utils;

import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public enum Builder {
    INSTANCE;

    Builder() {
        var gson = Utils.gson;
        var folder = ModLoaderThings.getConfigDir().toFile();
        var file = ModLoaderThings.getConfigDir().resolve("projectsavethepets.json").toFile();
        var authenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY);
        var profileRepository = authenticationService.createProfileRepository();
        var sessionService = authenticationService.createMinecraftSessionService();

        if (!folder.exists() && !folder.mkdirs()) {
            throw new IOError(new IOException("Failed to create configuration directory"));
        }


    }
}
