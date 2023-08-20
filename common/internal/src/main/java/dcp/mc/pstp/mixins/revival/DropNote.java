package dcp.mc.pstp.mixins.revival;

import dcp.mc.old.config.Config;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.registry.Registries;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
final class DropNote {
    @SuppressWarnings("deprecation")
    private boolean isValid(Entity entity) {
        if (dcp.mc.old.ProjectSaveThePets.INSTANCE.isPet(entity)) {
            return true;
        }

        if (entity.hasCustomName() && Config.INSTANCE.getRevival().isNamedEntities()) {
            return true;
        }

        for (String revivableEntities : Config.INSTANCE.getRevival().getEntityWhitelist()) {
            if (entity.getType().getRegistryEntry().matchesId(dcp.mc.old.Utilities.INSTANCE.convertToId(revivableEntities))) {
                return true;
            }
        }

        return false;
    }

    @SuppressWarnings("deprecation")
    @Inject(method = "drop", at = @At(value = "HEAD"))
    private void dropNote(DamageSource source, CallbackInfo ci) {
        if (!Config.INSTANCE.getRevival().isEnabled()) {
            return;
        }

        LivingEntity instance = (LivingEntity)(Object)this;

        if (!isValid(instance)) {
            return;
        }

        for (String entity : Config.INSTANCE.getRevival().getEntityBlacklist()) {
            if (instance.getType().getRegistryEntry().matchesId(dcp.mc.old.Utilities.INSTANCE.convertToId(entity))) {
                return;
            }
        }

        ItemStack note = new ItemStack(Items.PAPER, 1);
        ItemEntity noteEntity = new ItemEntity(instance.getWorld(), instance.getX(), instance.getY(), instance.getZ(), note);
        NbtCompound entityNbt = new NbtCompound();

        NbtCompound itemNbt = new NbtCompound();
        NbtCompound displayNbt = new NbtCompound();
        NbtCompound data = new NbtCompound();
        NbtList loreNbt = new NbtList();

        loreNbt.add(NbtString.of(Text.Serializer.toJson(Text.translatable("lore.projectsavethepets.dying-note.1"))));
        loreNbt.add(NbtString.of(Text.Serializer.toJson(Text.translatable("lore.projectsavethepets.dying-note.2", instance.getName()))));
        loreNbt.add(NbtString.of(Text.Serializer.toJson(Text.translatable("lore.projectsavethepets.dying-note.3"))));
        loreNbt.add(NbtString.of(Text.Serializer.toJson(Text.translatable("lore.projectsavethepets.dying-note.4", instance.getType().getName()))));

        displayNbt.put("Name", NbtString.of("\"A Dying Pet's Note\""));
        displayNbt.put("Lore", loreNbt);

        instance.writeNbt(data);

        dcp.mc.old.ProjectSaveThePets.INSTANCE.setupNbt((Entity)(Object)this, data);

        itemNbt.put("display", displayNbt);
        itemNbt.put("data", data);
        itemNbt.putString("type", Registries.ENTITY_TYPE.getId(instance.getType()).toString());

        note.setNbt(itemNbt);

        noteEntity.writeNbt(entityNbt);
        entityNbt.putInt("Invulnerable", 1);
        noteEntity.readNbt(entityNbt);

        instance.getWorld().spawnEntity(noteEntity);
    }
}
