package dcp.mc.pstp.mixins.protection;

import dcp.mc.pstp.ProjectSaveThePets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ProjectileEntity.class)
abstract class Projectile {
    private Projectile() {
        throw new AssertionError();
    }
    @Shadow
    public @Nullable
    abstract Entity getOwner();

    @Inject(method = "canHit", at = @At(value = "HEAD"), cancellable = true)
    private void preventCollision(Entity target, CallbackInfoReturnable<Boolean> cir) {
        if (!ProjectSaveThePets.CONFIG.get().damage().projectiles()) {
            return;
        }

        Entity attacker = this.getOwner();

        if (!(attacker instanceof LivingEntity livingEntity)) {
            return;
        }

        if (ProjectSaveThePets.preventDamage(livingEntity, target)) {
            cir.setReturnValue(false);
        }
    }
}
