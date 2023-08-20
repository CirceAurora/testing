package dcp.mc.pstp.api;

import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

public interface PreventDamagePredicate<T extends LivingEntity> extends Base<T> {
    boolean preventDamage(@NotNull LivingEntity attacker, @NotNull T target);
}
