package dcp.mc.pstp.config;

public record EnvironmentalProtection(
        boolean explosions,
        boolean bushes,
        boolean fires,
        boolean freezing,
        boolean drowning,
        boolean fall
)
{
    EnvironmentalProtection() {
        this(false, false, false, false, false, false);
    }
}
