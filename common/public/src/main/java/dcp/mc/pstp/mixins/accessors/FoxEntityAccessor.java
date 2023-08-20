package dcp.mc.pstp.mixins.accessors;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.passive.FoxEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@SuppressWarnings("UnusedMixin")
@Mixin(FoxEntity.class)
public interface FoxEntityAccessor {
    @Accessor("OWNER")
    static TrackedData<Optional<UUID>> getOwner() {
        throw new AssertionError();
    }

    @Accessor("OTHER_TRUSTED")
    static TrackedData<Optional<UUID>> getOtherTrusted() {
        throw new AssertionError();
    }

    @Invoker("getTrustedUuids")
    List<UUID> getTrustedUuids();
}
