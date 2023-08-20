package dcp.mc.pstp.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.Proxy;
import java.nio.file.Path;
import java.util.Arrays;

import dcp.mc.pstp.Utilities;

import com.google.gson.annotations.SerializedName;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import org.jetbrains.annotations.NotNull;

public record Config(
        @NotNull DamageProtection damage,
        @NotNull EnvironmentalProtection environmental,
        @NotNull EffectsProtection effects,
        @SerializedName("protected") @NotNull ProtectedEntities protectedEntities,
        @NotNull Revival revival,
        @NotNull Ownership ownership,
        @NotNull Player @NotNull [] blacklist,
        @NotNull Player @NotNull [] whitelist
)
{
    public static Config DEFAULT = new Config(new DamageProtection(),
            new EnvironmentalProtection(),
            new EffectsProtection(),
            new ProtectedEntities(),
            new Revival(),
            new Ownership(),
            new Player[0],
            new Player[0]
    );

    public static Config getInstance(Path configDir) {
        var folder = configDir.toFile();
        var file = configDir.resolve("projectsavethepets.json").toFile();
        var authenticationService = new YggdrasilAuthenticationService(Proxy.NO_PROXY);
        var profileRepository = authenticationService.createProfileRepository();
        var sessionService = authenticationService.createMinecraftSessionService();

        if (!folder.exists() && !folder.mkdirs()) {
            Utilities.LOGGER.error("Failed to create the configuration directory. Using default configs. Please manually create: " + folder.getAbsolutePath());
            return DEFAULT;
        }

        if (!file.exists()) {
            write(file, DEFAULT);
            return DEFAULT;
        }

        try (FileReader reader = new FileReader(file)) {
            var config = Utilities.GSON.fromJson(reader, Config.class);

            return new Config(config.damage,
                    config.environmental,
                    config.effects,
                    config.protectedEntities,
                    config.revival,
                    config.ownership,
                    Arrays.stream(config.blacklist).map(player -> player.fillMissing(profileRepository, sessionService)).toArray(Player[]::new),
                    Arrays.stream(config.whitelist).map(player -> player.fillMissing(profileRepository, sessionService)).toArray(Player[]::new)
            );
        } catch (FileNotFoundException e) {
            Utilities.LOGGER.error("Config file was not found after checks. Please report!", e);
        } catch (IOException e) {
            Utilities.LOGGER.error("Failed to read the configuration file. Please check the file permission: " + file.getAbsolutePath(), e);
        }

        return DEFAULT;
    }

    private static void write(File file, Config config) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(Utilities.GSON.toJson(config));
        } catch (IOException e) {
            Utilities.LOGGER.error("Failed to write the configuration file. Please check the file permission: " + file.getAbsolutePath(), e);
        }
    }
}
