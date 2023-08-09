package dcp.mc.pstp.fabric.mixins;

import java.nio.file.Path;

import dcp.mc.pstp.config.Builder;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Builder.class)
final class ConfigDirectory {
    @Inject(method = "configDirectory", at=@At("HEAD"), cancellable = true)
    private void lol(CallbackInfoReturnable<Path> cir) {
        cir.setReturnValue(Path.of("home here"));
        cir.cancel();
    }
}
