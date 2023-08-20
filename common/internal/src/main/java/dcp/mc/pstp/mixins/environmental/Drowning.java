package dcp.mc.pstp.mixins.environmental;

import dcp.mc.pstp.ProjectSaveThePets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = LivingEntity.class)
final class Drowning {
    private Drowning() {
        throw new AssertionError();
    }
    @Inject(method = "canBreatheInWater", at = @At(value = "HEAD"), cancellable = true)
    private void preventDrowning(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (ProjectSaveThePets.CONFIG.get().environmental().drowning() && ProjectSaveThePets.isPet((Entity)(Object)this)) {
            callbackInfoReturnable.setReturnValue(true);
        }
    }
}
