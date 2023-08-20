package dcp.mc.pstp.config;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import com.mojang.authlib.Agent;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.ProfileLookupCallback;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import net.minecraft.util.Uuids;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record Player(
        @Nullable String name,
        @Nullable String online,
        @Nullable String offline
)
{
    public boolean uuidEqual(@NotNull UUID uuid) {
        return uuid.toString().equals(online) || uuid.toString().equals(offline);
    }

    public Player fillMissing(@NotNull GameProfileRepository repository, @NotNull MinecraftSessionService service) {
        if (name == null && online == null && offline == null) {
            throw new IllegalStateException("name, online_uuid, and offline_uuid cannot all be null");
        }

        if (name == null && online == null) {
            return this;
        }

        var newName = name;
        var newOnline = online;
        var newOffline = offline;

        if (name != null && online == null) {
            var uuid = new AtomicReference<String>(null);

            repository.findProfilesByNames(new String[] { name }, Agent.MINECRAFT, new ProfileLookupCallback() {
                @Override
                public void onProfileLookupSucceeded(GameProfile profile) {
                    uuid.set(profile.getId().toString());
                }

                @Override
                public void onProfileLookupFailed(GameProfile profile, Exception exception) {

                }
            });

            newOnline = uuid.get();
        }

        if (name == null) {
            newName = service.fillProfileProperties(new GameProfile(UUID.fromString(online), null), false).getName();
        }

        if (newName != null) {
            newOffline = Uuids.getOfflinePlayerUuid(newName).toString();
        }

        return new Player(newName, newOnline, newOffline);
    }
}
