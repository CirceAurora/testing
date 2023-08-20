package dcp.mc.pstp.mixins.environmental;

import dcp.mc.pstp.ProjectSaveThePets;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
final class Freezing {
    private Freezing() {
        throw new AssertionError();
    }
    @Inject(method = "canFreeze", at = @At(value = "HEAD"), cancellable = true)
    private void preventFreezing(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (ProjectSaveThePets.CONFIG.get().environmental().freezing() && ProjectSaveThePets.isPet((Entity)(Object)this)) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
}
