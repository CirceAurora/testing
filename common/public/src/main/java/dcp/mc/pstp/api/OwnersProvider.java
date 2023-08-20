package dcp.mc.pstp.api;

import java.util.UUID;

import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public interface OwnersProvider<T extends LivingEntity> extends Base<T> {
    @NotNull UUID @NotNull [] getOwners(@NotNull T entity);
}
