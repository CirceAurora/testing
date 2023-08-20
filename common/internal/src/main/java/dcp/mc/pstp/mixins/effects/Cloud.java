package dcp.mc.pstp.mixins.effects;

import dcp.mc.pstp.ProjectSaveThePets;
import dcp.mc.pstp.Utilities;

import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.registry.Registries;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Internal
@Mixin(value = LivingEntity.class, priority = 1500)
abstract class Cloud {
    private Cloud() {
        throw new AssertionError();
    }

    @Shadow
    public abstract boolean isUndead();

    @Inject(method = "addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;Lnet/minecraft/entity/Entity;)Z", at = @At(value = "HEAD"), cancellable = true)
    private void preventEffect(StatusEffectInstance instance, Entity source, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (!(source instanceof AreaEffectCloudEntity areaEffectCloud)) {
            return;
        }

        if (!ProjectSaveThePets.CONFIG.get().effects().enabled()) {
            return;
        }

        if (!ProjectSaveThePets.preventDamage(areaEffectCloud.getOwner(), (LivingEntity)(Object) this)) {
            return;
        }

        String[] effects = ProjectSaveThePets.CONFIG.get().effects().getEffects(isUndead());

        for (String effect : effects) {
            if (instance.getEffectType().equals(Registries.STATUS_EFFECT.get(Utilities.convertToId(effect)))) {
                callbackInfoReturnable.setReturnValue(false);
                return;
            }
        }
    }
}
