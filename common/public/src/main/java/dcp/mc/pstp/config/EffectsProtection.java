package dcp.mc.pstp.config;

import org.jetbrains.annotations.NotNull;

public record EffectsProtection(
        boolean enabled,
        @NotNull String @NotNull [] living,
        @NotNull String @NotNull [] undead
)
{
    EffectsProtection() {
        this(true, new String[] { "minecraft:instant_damage", "minecraft:poison" }, new String[] { "minecraft:instant_health", "minecraft:regeneration" });
    }

    public @NotNull String @NotNull [] getEffects(boolean isUndead) {
        return isUndead ? undead : living;
    }
}
