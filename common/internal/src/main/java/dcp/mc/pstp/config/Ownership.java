package dcp.mc.pstp.config;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.NotNull;

@Internal
public record Ownership(
        boolean removal,
        boolean adding,
        boolean transfer,
        boolean defaultShears,
        @NotNull String @NotNull [] shears,
        boolean defaultStick,
        @NotNull String @NotNull [] sticks
)
{
    Ownership() {
        this(true, true, false, true, new String[0], true, new String[0]);
    }
}
