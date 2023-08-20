package dcp.mc.pstp.mixins.environmental;

import dcp.mc.pstp.ProjectSaveThePets;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
final class Explosions {
    private Explosions() {
        throw new AssertionError();
    }
    @Inject(method = "isImmuneToExplosion", at = @At(value = "HEAD"), cancellable = true)
    private void preventExplosionDamage(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (ProjectSaveThePets.CONFIG.get().environmental().explosions() && ProjectSaveThePets.isPet((Entity)(Object)this)) {
            callbackInfoReturnable.setReturnValue(true);
        }
    }
}
