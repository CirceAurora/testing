package dcp.mc.pstp.fabric;

import java.util.function.Consumer;

import dcp.mc.pstp.ProjectSaveThePets;
import dcp.mc.pstp.api.ForceDamagePredicate;
import dcp.mc.pstp.api.NoteNbtConsumer;
import dcp.mc.pstp.api.OwnersProvider;
import dcp.mc.pstp.api.PetManager;
import dcp.mc.pstp.api.PetPredicate;
import dcp.mc.pstp.api.PreventDamagePredicate;
import dcp.mc.pstp.config.Config;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.impl.FabricLoaderImpl;
import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public final class Main
        implements ModInitializer {
    @Override
    public void onInitialize() {
        ProjectSaveThePets.CONFIG.set(Config.getInstance(FabricLoaderImpl.INSTANCE.getConfigDir()));

        load("fdp", ForceDamagePredicate.class, ProjectSaveThePets.FORCE_DAMAGE_PREDICATES::add);
        load("nnc", NoteNbtConsumer.class, ProjectSaveThePets.NOTE_NBT_CONSUMERS::add);
        load("op", OwnersProvider.class, ProjectSaveThePets.OWNERS_PROVIDERS::add);
        load("pm", PetManager.class, ProjectSaveThePets.PET_MANAGERS::add);
        load("pp", PetPredicate.class, ProjectSaveThePets.PET_PREDICATES::add);
        load("pdp", PreventDamagePredicate.class, ProjectSaveThePets.PREVENT_DAMAGE_PREDICATES::add);
    }

    private <T> void load(String key, Class<T> clazz, Consumer<T> func) {
        FabricLoaderImpl.INSTANCE.getEntrypoints("pstp-" + key, clazz).forEach(func);
    }
}
