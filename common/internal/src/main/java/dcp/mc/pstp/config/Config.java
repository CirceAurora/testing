package dcp.mc.pstp.config;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

@Internal
public record Config(
        @NotNull DamageProtection damage,
        @NotNull EnvironmentalProtection environmental,
        @NotNull EffectsProtection effects,
        @SerializedName("protected") @NotNull ProtectedEntities protectedEntities,
        @NotNull Revival revival,
        @NotNull Ownership ownership,
        @NotNull Player @NotNull [] blacklist,
        @NotNull Player @NotNull [] whitelist
) {}
