package dcp.mc.pstp.mixins.protection;

import dcp.mc.pstp.ProjectSaveThePets;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.explosion.Explosion;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Explosion.class)
abstract class Explosions {
    private Explosions() {
        throw new AssertionError();
    }

    @Shadow
    @Nullable
    public abstract LivingEntity getCausingEntity();

    @Redirect(method = "collectBlocksAndDamageEntities", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;squaredDistanceTo(Lnet/minecraft/util/math/Vec3d;)D"))
    private double preventExplosionDamage(Entity instance, Vec3d vector) {
        if (pstp$isProtected(instance)) {
            return Double.MAX_VALUE;
        } else {
            return instance.squaredDistanceTo(vector);
        }
    }

    @Unique
    private boolean pstp$isProtected(Entity instance) {
        if (!ProjectSaveThePets.CONFIG.get().damage().explosions()) {
            return false;
        }

        return ProjectSaveThePets.preventDamage(getCausingEntity(), instance);
    }
}
