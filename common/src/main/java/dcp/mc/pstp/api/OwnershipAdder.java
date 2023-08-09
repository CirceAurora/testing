package dcp.mc.pstp.api;

import java.util.UUID;

import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface OwnershipAdder<T extends Entity> {
    @NotNull Class<T> type();

    boolean addOwnership(@NotNull T entity, @NotNull UUID newOwner);
}
