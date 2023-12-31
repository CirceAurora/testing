package dcp.mc.pstp.mixins.accessors;

import net.minecraft.entity.Entity;
import net.minecraft.entity.data.DataTracker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@SuppressWarnings("UnusedMixin")
@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor("dataTracker")
    DataTracker getDataTracker();
}
