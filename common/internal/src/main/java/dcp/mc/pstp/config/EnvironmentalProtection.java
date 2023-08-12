package dcp.mc.pstp.config;

import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public record EnvironmentalProtection(
        boolean explosions,
        boolean bushes,
        boolean fires,
        boolean freezing,
        boolean drowning
)
{
    EnvironmentalProtection() {
        this(false, false, false, false, false);
    }
}
