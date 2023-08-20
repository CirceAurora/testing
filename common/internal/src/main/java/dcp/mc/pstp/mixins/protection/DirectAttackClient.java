package dcp.mc.pstp.mixins.protection;

import dcp.mc.pstp.ProjectSaveThePets;
import dcp.mc.pstp.Utilities;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(ClientPlayerInteractionManager.class)
final class DirectAttackClient {
    private DirectAttackClient() {
        throw new AssertionError();
    }
    @Inject(method = "attackEntity", at = @At(value = "HEAD"), cancellable = true)
    private void preventAttack(PlayerEntity player, Entity target, CallbackInfo callbackInfo) {
        if (!ProjectSaveThePets.CONFIG.get().damage().direct()) {
            return;
        }

        if (Utilities.isKeyDown()) {
            return;
        }

        if (ProjectSaveThePets.preventDamage(player, target)) {
            callbackInfo.cancel();
        }
    }
}
