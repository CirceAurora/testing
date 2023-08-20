package dcp.mc.pstp.fabric;

import dcp.mc.pstp.ProjectSaveThePets;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import org.jetbrains.annotations.ApiStatus.Internal;
import org.lwjgl.glfw.GLFW;

@Internal
public class Client implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ProjectSaveThePets.DAMAGE_KEY_BINDING.set(KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.pstp.allowdamage",
                GLFW.GLFW_KEY_LEFT_SHIFT,
                "key.category.pstp")));
    }
}
