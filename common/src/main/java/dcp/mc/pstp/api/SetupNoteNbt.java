package dcp.mc.pstp.api;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

public interface SetupNoteNbt<T extends Entity> {
    @NotNull Class<T> type();

    void setupNbt(@NotNull T entity, @NotNull NbtCompound nbt);
}
