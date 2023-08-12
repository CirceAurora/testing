package dcp.mc.pstp.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

public interface ForceDamagePredicate<T extends Entity> extends Base<T> {
    boolean forceDamage(@NotNull PlayerEntity attacker, @NotNull T target, @NotNull DamageSource source);
}
