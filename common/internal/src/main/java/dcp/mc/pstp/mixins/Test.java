package dcp.mc.pstp.mixins;

import dcp.mc.pstp.ModLoaderThings;

import net.minecraft.client.main.Main;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Main.class)
final class Test {
    @Inject(method = "main", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;initRenderThread()V"))
    private static void test(String[] args, CallbackInfo ci) {
        var logger = LoggerFactory.getLogger("TEST");

        logger.error("########################");
        logger.error("       CONFIG DIR       ");
        logger.error(ModLoaderThings.getConfigDir().toString());
        logger.error("########################");
    }
}
