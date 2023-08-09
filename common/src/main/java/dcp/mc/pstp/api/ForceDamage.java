package dcp.mc.pstp.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface ForceDamage {
    boolean forceDamage(@NotNull PlayerEntity attacker, @NotNull Entity target, @NotNull DamageSource source);
}
