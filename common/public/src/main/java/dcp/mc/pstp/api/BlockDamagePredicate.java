package dcp.mc.pstp.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

public interface BlockDamagePredicate<T extends Entity> extends Base<T> {
    boolean blockDamage(@NotNull PlayerEntity attacker, @NotNull T target, @NotNull DamageSource source);
}
