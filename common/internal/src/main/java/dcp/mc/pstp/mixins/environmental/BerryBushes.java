package dcp.mc.pstp.mixins.environmental;

import dcp.mc.pstp.ProjectSaveThePets;

import net.minecraft.block.BlockState;
import net.minecraft.block.SweetBerryBushBlock;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = SweetBerryBushBlock.class)
final class BerryBushes {
    private BerryBushes() {
        throw new AssertionError();
    }
    @Inject(method = "onEntityCollision", at = @At(value = "HEAD"), cancellable = true)
    void preventDamage(BlockState state, World world, BlockPos pos, Entity entity, CallbackInfo callbackInfo) {
        if (ProjectSaveThePets.CONFIG.get().environmental().bushes() && ProjectSaveThePets.isPet(entity)) {
            callbackInfo.cancel();
        }
    }
}
