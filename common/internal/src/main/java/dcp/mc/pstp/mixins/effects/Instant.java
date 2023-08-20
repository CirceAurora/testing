package dcp.mc.pstp.mixins.effects;

import dcp.mc.pstp.ProjectSaveThePets;
import dcp.mc.pstp.Utilities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StatusEffect.class)
final class Instant {
    private Instant() {
        throw new AssertionError();
    }
    @Inject(method = "applyInstantEffect", at = @At(value = "HEAD"), cancellable = true)
    private void preventEffect(Entity source, Entity attacker, LivingEntity target, int amplifier, double proximity, CallbackInfo callbackInfo) {
        if (!ProjectSaveThePets.CONFIG.get().effects().enabled()) {
            return;
        }

        if(!(attacker instanceof LivingEntity livingEntity)) {
            return;
        }

        if (!ProjectSaveThePets.preventDamage(livingEntity, target)) {
            return;
        }

        StatusEffect instance = (StatusEffect)(Object)this;
        String[] effects = ProjectSaveThePets.CONFIG.get().effects().getEffects(target.isUndead());

        for (String effect : effects) {
            if (instance.equals(Registries.STATUS_EFFECT.get(Utilities.convertToId(effect)))) {
                callbackInfo.cancel();
                return;
            }
        }
    }
}
