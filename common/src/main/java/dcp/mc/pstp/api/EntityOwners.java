package dcp.mc.pstp.api;

import java.util.UUID;

import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface EntityOwners<T extends Entity> {
    @NotNull UUID @NotNull [] getOwners(@NotNull T entity);
}
