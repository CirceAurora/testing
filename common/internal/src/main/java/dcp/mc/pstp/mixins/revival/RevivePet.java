package dcp.mc.pstp.mixins.revival;

import dcp.mc.old.config.Config;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
final class RevivePet {
    @Inject(method = "useOnBlock", at = @At(value = "HEAD"), cancellable = true)
    private void revivePet(ItemUsageContext context, CallbackInfoReturnable<ActionResult> cir) {
        if (!Config.INSTANCE.getRevival().isEnabled()) {
            return;
        }

        World world = context.getWorld();
        ItemStack stack = context.getStack();
        BlockState blockState = world.getBlockState(context.getBlockPos());
        NbtCompound nbt = stack.getNbt();

        if (nbt == null) {
            return;
        }

        if (!stack.getItem().equals(Items.PAPER)) {
            return;
        }

        if (!nbt.contains("type")) {
            return;
        }

        if (!checkBlock(blockState)) {
            return;
        }

        world.breakBlock(context.getBlockPos(), false);
        stack.decrement(1);

        if (world.isClient) {
            cir.setReturnValue(ActionResult.SUCCESS);
            return;
        }

        // Common Entity vars
        double x = context.getBlockPos().getX() + 0.5;
        double y = context.getBlockPos().getY();
        double z = context.getBlockPos().getZ() + 0.5;

        // Create Entity
        Entity entity = Registries.ENTITY_TYPE.get(dcp.mc.old.Utilities.INSTANCE.convertToId(nbt.getString("type"))).create(world);
        assert entity != null;
        entity.readNbt(nbt.getCompound("data"));
        entity.setPos(x, y + 0.5, z);
        world.spawnEntity(entity);

        // Create Lighting
        LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
        assert lightning != null;
        lightning.setCosmetic(true);
        lightning.setPos(x, y, z);
        world.spawnEntity(lightning);

        // Summon particles
        ((ServerWorld)world).spawnParticles(ParticleTypes.EXPLOSION_EMITTER, x, y, z, 1, 0, 0, 0, 1);

        cir.setReturnValue(ActionResult.CONSUME);
    }


    @SuppressWarnings("deprecation")
    private boolean checkBlock(BlockState blockState) {
        for (String block : Config.INSTANCE.getRevival().getRevivalBlocks()) {
            if (blockState.getBlock().getRegistryEntry().matchesId(dcp.mc.old.Utilities.INSTANCE.convertToId(block))) {
                return true;
            }
        }

        return false;
    }
}
