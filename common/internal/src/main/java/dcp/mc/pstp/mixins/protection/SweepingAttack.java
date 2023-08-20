package dcp.mc.pstp.mixins.protection;

import dcp.mc.pstp.ProjectSaveThePets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PlayerEntity.class)
final class SweepingAttack {
    private SweepingAttack() {
        throw new AssertionError();
    }
    @Redirect(method = "attack", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isTeammate(Lnet/minecraft/entity/Entity;)Z"))
    private boolean preventAttack(PlayerEntity player, Entity target) {
        if (!pstp$isProtected(player, target)) {
            return player.isTeammate(target);
        }

        return true;
    }

    @Unique
    private boolean pstp$isProtected(PlayerEntity player, Entity target) {
        if (!ProjectSaveThePets.CONFIG.get().damage().sweeping()) {
            return false;
        }

        return ProjectSaveThePets.preventDamage(player, target);
    }
}
