package dcp.mc.pstp.mixins.protection;

import dcp.mc.pstp.ProjectSaveThePets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
final class DirectAttackServer {
    private DirectAttackServer() {
        throw new AssertionError();
    }
    @Inject(method = "handleAttack", at = @At("HEAD"), cancellable = true)
    private void preventAttack(Entity attacker, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (!(attacker instanceof LivingEntity livingEntity)) {
            return;
        }

        if (ProjectSaveThePets.preventDamage(livingEntity, (Entity) (Object) this)) {
            callbackInfoReturnable.setReturnValue(true);
            callbackInfoReturnable.cancel();
        }
    }
}
