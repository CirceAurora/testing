package dcp.mc.pstp;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import dcp.mc.pstp.api.Base;
import dcp.mc.pstp.mixins.accessors.KeyBindingAccessor;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public enum Utilities {
    ;
    public static final Gson GSON = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                                                     .setPrettyPrinting()
                                                     .disableHtmlEscaping()
                                                     .create();
    public static final Logger LOGGER = LoggerFactory.getLogger("PSTP");
    private static final Map<String, Identifier> IDENTIFIER_CACHE = new ConcurrentHashMap<>();

    public static boolean isKeyDown() {
        var keyBinding = ProjectSaveThePets.DAMAGE_KEY_BINDING.get();
        var sneakKey = ((KeyBindingAccessor) MinecraftClient.getInstance().options.sneakKey).getBoundKey();
        var allowDamageKey = ((KeyBindingAccessor) keyBinding).getBoundKey();

        return sneakKey.getCode() == allowDamageKey.getCode() && sneakKey.getCategory() == allowDamageKey.getCategory() || keyBinding.isPressed();
    }

    public static <T extends LivingEntity> @Nullable T getT(@NotNull Base<T> base, @NotNull Entity entity) {
        return base.getType().isInstance(entity) ? base.getType().cast(entity) : null;
    }

    public static Identifier convertToId(String id) {
        return IDENTIFIER_CACHE.computeIfAbsent(id, key -> Identifier.splitOn(key, ':'));
    }
}
