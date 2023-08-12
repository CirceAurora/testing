package dcp.mc.pstp.config;

import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public record DamageProtection(
        boolean projectiles,
        boolean sweeping,
        boolean direct,
        boolean explosions,
        boolean vanillaTeams
)
{
    DamageProtection() {
        this(true, true, true, true, false);
    }
}
