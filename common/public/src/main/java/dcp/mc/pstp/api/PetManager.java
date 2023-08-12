package dcp.mc.pstp.api;

import java.util.UUID;

import net.minecraft.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface PetManager<T extends Entity>
        extends OwnersProvider<T>, Base<T>
{
    default boolean removeOwner(@NotNull T entity, @NotNull UUID owner) {
        return false;
    }
    default boolean addOwner(@NotNull T entity, @NotNull UUID owner) {
        return false;
    }

    default boolean transferOwner(@NotNull T entity, @NotNull UUID oldOwner, @NotNull UUID newOwner) {
        return removeOwner(entity, oldOwner) && addOwner(entity, newOwner);
    }

    default void removeOwners(@NotNull T entity, @NotNull UUID @NotNull [] owners) {
        for (var owner : owners) {
            removeOwner(entity, owner);
        }
    }

    default void addOwners(@NotNull T entity, @NotNull UUID @NotNull [] owners) {
        for (var owner : owners) {
            addOwner(entity, owner);
        }
    }

    default void clearOwners(@NotNull T entity) {
        removeOwners(entity, getOwners(entity));
    }
}
