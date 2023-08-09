package dcp.mc.pstp.mixins;

import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Main.class)
final class Test {
    private Test() {
        throw new AssertionError("Constructor should not be called");
    }
//    @Inject(method = "main", at = @At("HEAD"))
//    private static void init(String[] args, CallbackInfo ci) {
//        System.out.println("######### HELLO ###########");
//        System.out.println(Builder.INSTANCE.getConfigDirectory());
//        System.out.println("#########  BYE  ###########");
//    }
}
