package dcp.mc.pstp.api;

import java.util.UUID;

import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface OwnershipManager<T extends Entity> extends OwnershipAdder<T>, OwnershipRemoval<T>, OwnershipTransfer<T> {
    @NotNull Class<T> type();

    @Override
    default boolean transferOwnership(@NotNull T entity, @NotNull UUID oldOwner, @NotNull UUID newOwner) {
        return removeOwnership(entity, oldOwner) && addOwnership(entity, newOwner);
    }
}
