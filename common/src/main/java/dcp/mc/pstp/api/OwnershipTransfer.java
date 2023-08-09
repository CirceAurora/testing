package dcp.mc.pstp.api;

import java.util.UUID;

import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface OwnershipTransfer<T extends Entity> {
    @NotNull Class<T> type();

    boolean transferOwnership(@NotNull T entity, @NotNull UUID oldOwner, @NotNull UUID newOwner);
}
