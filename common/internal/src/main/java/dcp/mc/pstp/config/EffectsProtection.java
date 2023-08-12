package dcp.mc.pstp.config;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

@Internal
public record EffectsProtection(
        boolean enabled,
        @NotNull String @NotNull [] living,
        @NotNull String @NotNull [] undead
)
{
    EffectsProtection() {
        this(true, new String[] { "minecraft:instant_damage", "minecraft:poison" }, new String[] { "minecraft:instant_health", "minecraft:regeneration" });
    }
}
