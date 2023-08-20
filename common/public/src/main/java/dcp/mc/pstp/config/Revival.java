package dcp.mc.pstp.config;

import org.jetbrains.annotations.NotNull;

public record Revival(
        boolean enabled,
        boolean named,
        @NotNull String @NotNull [] blocks,
        @NotNull String @NotNull [] blacklist,
        @NotNull String @NotNull [] whitelist
)
{
    Revival() {
        this(true, true, new String[] { "minecraft:copper_block" }, new String[0], new String[0]);
    }
}
