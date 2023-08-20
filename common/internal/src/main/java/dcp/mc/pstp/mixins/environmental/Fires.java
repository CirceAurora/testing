package dcp.mc.pstp.mixins.environmental;

import dcp.mc.pstp.ProjectSaveThePets;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
final class Fires {
    private Fires() {
        throw new AssertionError();
    }
    @Inject(method = "isFireImmune", at = @At(value = "HEAD"), cancellable = true)
    private void preventFireDamage(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (ProjectSaveThePets.CONFIG.get().environmental().fires() && ProjectSaveThePets.isPet((Entity)(Object)this)) {
            callbackInfoReturnable.setReturnValue(true);
        }
    }
}
