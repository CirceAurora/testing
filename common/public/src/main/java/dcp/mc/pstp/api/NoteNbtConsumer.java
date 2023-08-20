package dcp.mc.pstp.api;

import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import org.jetbrains.annotations.NotNull;

public interface NoteNbtConsumer<T extends LivingEntity> extends Base<T> {
    void setupNbt(@NotNull T entity, @NotNull NbtCompound nbt);
}
