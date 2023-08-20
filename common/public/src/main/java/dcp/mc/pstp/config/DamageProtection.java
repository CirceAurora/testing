package dcp.mc.pstp.config;

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
