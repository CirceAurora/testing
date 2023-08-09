package dcp.mc.pstp.api;

import java.util.UUID;

import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface OwnershipRemoval<T extends Entity> {
    @NotNull Class<T> type();

    boolean removeOwnership(@NotNull T entity, @NotNull UUID owner);
}
