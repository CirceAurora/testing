package dcp.mc.pstp;

import java.nio.file.Path;
import java.util.Collections;
import java.util.Set;

import dcp.mc.pstp.api.BlockDamagePredicate;
import dcp.mc.pstp.api.ForceDamagePredicate;
import dcp.mc.pstp.api.NoteNbtConsumer;
import dcp.mc.pstp.api.OwnersProvider;
import dcp.mc.pstp.api.PetManager;
import dcp.mc.pstp.errors.MustBeInjectedException;
import dcp.mc.pstp.errors.StaticOnlyInitialization;

import net.minecraft.client.option.KeyBinding;
import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public abstract class ModLoaderThings {
    private ModLoaderThings() {
        throw new StaticOnlyInitialization();
    }
    public static Path getConfigDir() {
        throw new MustBeInjectedException();
    }

    public static KeyBinding getDamageKeyBinding() {
        throw new MustBeInjectedException();
    }

    public static Set<BlockDamagePredicate<?>> getBlockDamagePredicates() {
        return Collections.emptySet();
    }

    public static Set<ForceDamagePredicate<?>> getForceDamagePredicates() {
        return Collections.emptySet();
    }

    public static Set<NoteNbtConsumer<?>> getNoteNbtConsumers() {
        return Collections.emptySet();
    }

    public static Set<OwnersProvider<?>> getOwnersProviders() {
        return Collections.emptySet();
    }

    public static Set<PetManager<?>> getPetManagers() {
        return Collections.emptySet();
    }
}
