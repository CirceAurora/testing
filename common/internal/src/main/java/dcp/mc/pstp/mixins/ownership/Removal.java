package dcp.mc.pstp.mixins.ownership;

import dcp.mc.old.ProjectSaveThePets;
import dcp.mc.old.config.Config;

import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ShearsItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MobEntity.class)
final class Removal {
    @Inject(method = "interactMob", at = @At(value = "HEAD"), cancellable = true)
    private void removeOwnership(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> callbackInfoReturnable) {
        if (!Config.INSTANCE.getOwnership().isRemoval()) {
            return;
        }

        if (player.getWorld().isClient()) {
            return;
        }

        if (!player.isSneaking()) {
            return;
        }

        Entity instance = (Entity)(Object)this;
        ItemStack itemStack = player.getStackInHand(hand);

        if (!isShear(itemStack)) {
            return;
        }

        if (ProjectSaveThePets.INSTANCE.removeOwnership(player, instance)) {
            player.getWorld().playSoundFromEntity(null, instance, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 1.0F, 1.0F);

            if (itemStack.isDamageable()) {
                itemStack.damage(1, player, (playerEntity) -> playerEntity.sendToolBreakStatus(hand));
            }

            callbackInfoReturnable.setReturnValue(ActionResult.SUCCESS);
        }
    }

    @SuppressWarnings("deprecation")
    private boolean isShear(ItemStack itemStack) {
        if (Config.INSTANCE.getOwnership().isDefaultShears() && itemStack.getItem() instanceof ShearsItem) {
            return true;
        }

        for (String shear : Config.INSTANCE.getOwnership().getExtraShears()) {
            if (itemStack.getItem().getRegistryEntry().matchesId(dcp.mc.old.Utilities.INSTANCE.convertToId(shear))) {
                return true;
            }
        }

        return false;
    }
}
