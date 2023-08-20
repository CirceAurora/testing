package dcp.mc.pstp.api;

import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public interface ForceDamagePredicate<T extends LivingEntity> extends Base<T> {
    boolean forceDamage(@NotNull LivingEntity attacker, @NotNull T target);
}
