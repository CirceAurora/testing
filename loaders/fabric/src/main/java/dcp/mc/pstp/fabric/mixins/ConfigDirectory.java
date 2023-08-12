package dcp.mc.pstp.fabric.mixins;

import java.nio.file.Path;

import dcp.mc.pstp.ModLoaderThings;
import dcp.mc.pstp.errors.StaticOnlyInitialization;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ModLoaderThings.class)
final class ConfigDirectory {
    private ConfigDirectory() {
        throw new StaticOnlyInitialization();
    }

    @Inject(method = "getConfigDir", at=@At("HEAD"), cancellable = true, remap = false)
    private static void lol(CallbackInfoReturnable<Path> cir) {
        cir.setReturnValue(Path.of("home here"));
        cir.cancel();
    }
}
