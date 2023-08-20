package dcp.mc.pstp.config;

import org.jetbrains.annotations.NotNull;

public record ProtectedEntities(
        boolean tamable,
        boolean fox,
        boolean horses,
        boolean players,
        @NotNull String @NotNull [] blacklist,
        @NotNull String @NotNull [] whitelist
)
{
    ProtectedEntities() {
        this(true, true, true, false, new String[0], new String[0]);
    }
}
