package dcp.mc.pstp;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicReference;

import dcp.mc.pstp.api.ForceDamagePredicate;
import dcp.mc.pstp.api.NoteNbtConsumer;
import dcp.mc.pstp.api.OwnersProvider;
import dcp.mc.pstp.api.PetManager;
import dcp.mc.pstp.api.PetPredicate;
import dcp.mc.pstp.api.PreventDamagePredicate;
import dcp.mc.pstp.config.Config;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.Registries;
import org.jetbrains.annotations.NotNull;

public enum ProjectSaveThePets {
    ;

    public static final Set<PreventDamagePredicate<?>> PREVENT_DAMAGE_PREDICATES = new CopyOnWriteArraySet<>();
    public static final Set<ForceDamagePredicate<?>> FORCE_DAMAGE_PREDICATES = new CopyOnWriteArraySet<>();
    public static final Set<NoteNbtConsumer<?>> NOTE_NBT_CONSUMERS = new CopyOnWriteArraySet<>();
    public static final Set<OwnersProvider<?>> OWNERS_PROVIDERS = new CopyOnWriteArraySet<>();
    public static final Set<PetManager<?>> PET_MANAGERS = new CopyOnWriteArraySet<>();
    public static final Set<PetPredicate<?>> PET_PREDICATES = new CopyOnWriteArraySet<>();
    public static final AtomicReference<@NotNull Config> CONFIG = new AtomicReference<>();
    public static final AtomicReference<@NotNull KeyBinding> DAMAGE_KEY_BINDING = new AtomicReference<>();

    private static boolean isBlacklisted(Entity entity) {
        for (var entry : CONFIG.get().protectedEntities().blacklist()) {
            if (Registries.ENTITY_TYPE.get(Utilities.convertToId(entry)).equals(entity.getType())) {
                return true;
            }
        }

        return false;
    }

    private static boolean isWhitelisted(Entity entity) {
        for (var entry : CONFIG.get().protectedEntities().whitelist()) {
            if (Registries.ENTITY_TYPE.get(Utilities.convertToId(entry)).equals(entity.getType())) {
                return true;
            }
        }

        return false;
    }

    private static boolean isBlacklistedPlayer(Entity entity) {
        if (!(entity instanceof PlayerEntity player)) {return false;}

        for (var entry : CONFIG.get().blacklist()) {
            if (entry.uuidEqual(player.getUuid())) {
                return true;
            }
        }

        return false;
    }

    private static boolean isWhitelistedPlayer(Entity entity) {
        if (!(entity instanceof PlayerEntity player)) {return false;}

        for (var entry : CONFIG.get().whitelist()) {
            if (entry.uuidEqual(player.getUuid())) {
                return true;
            }
        }

        return false;
    }

    public static boolean preventDamage(LivingEntity attacker, Entity target) {
        if (isBlacklisted(target) || isBlacklistedPlayer(attacker)) {
            return false;
        }

        if (isWhitelisted(target) || isWhitelistedPlayer(attacker)) {
            return true;
        }

        for (var predicate : FORCE_DAMAGE_PREDICATES) {
            if (!predicate.getType().isInstance(target)) {continue;}
            if (forceDamage(predicate, attacker, target)) {
                return false;
            }
        }

        for (var provider : OWNERS_PROVIDERS) {
            if (!provider.getType().isInstance(target)) {continue;}

            for (var predicate : PREVENT_DAMAGE_PREDICATES) {
                if (!predicate.getType().isInstance(target)) {continue;}

                for (var owner : getOwners(provider, target)) {
                    if (preventDamage(predicate, attacker, target)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static <T extends LivingEntity> boolean preventDamage(PreventDamagePredicate<T> predicate, LivingEntity attacker, Entity target) {
        var t = Utilities.getT(predicate, target);

        if (t == null) {
            return false;
        }

        return predicate.preventDamage(attacker, t);
    }

    private static <T extends LivingEntity> UUID[] getOwners(OwnersProvider<T> provider, Entity target) {
        var t = Utilities.getT(provider, target);

        if (t == null) {
            return new UUID[0];
        }

        return provider.getOwners(t);
    }

    private static <T extends LivingEntity> boolean forceDamage(ForceDamagePredicate<T> predicate, LivingEntity attacker, Entity target) {
        var t = Utilities.getT(predicate, target);

        if (t == null) {return false;}

        return predicate.forceDamage(attacker, t);
    }

    public static boolean isPet(Entity entity) {
        for (var predicate : PET_PREDICATES) {
            if (isPet(predicate, entity)) {return true;}
        }

        return false;
    }

    private static <T extends LivingEntity> boolean isPet(PetPredicate<T> predicate, Entity entity) {
        var t = Utilities.getT(predicate, entity);

        if (t == null) {
            return false;
        }

        return predicate.isPet(t);
    }
}
