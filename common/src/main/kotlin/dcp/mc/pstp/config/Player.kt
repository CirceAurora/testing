package dcp.mc.pstp.config

import java.util.UUID
import java.util.concurrent.atomic.AtomicReference
import com.mojang.authlib.Agent
import com.mojang.authlib.GameProfile
import com.mojang.authlib.GameProfileRepository
import com.mojang.authlib.ProfileLookupCallback
import com.mojang.authlib.minecraft.MinecraftSessionService
import net.minecraft.util.Uuids

@JvmRecord
data class Player(
    val name: String?,
    val offline: String?,
    val online: String?,
) {
    fun uuidEqual(uuid: String): Boolean {
        return uuid == online || uuid == offline
    }

    fun fillMissing(profileRepository: GameProfileRepository, sessionService: MinecraftSessionService): Player {
        if (name == null && online == null && offline == null) {
            throw IllegalStateException("name, online_uuid, and offline_uuid cannot all be null")
        }

        var newName = name
        var newOnline: String? = online
        var newOffline: String? = offline

        if (newOnline == null && newName != null) {
            val uuid = AtomicReference<String?>(null)
            profileRepository.findProfilesByNames(arrayOf(newName), Agent.MINECRAFT, object : ProfileLookupCallback {
                override fun onProfileLookupSucceeded(profile: GameProfile) {
                    uuid.set(profile.id.toString())
                }

                override fun onProfileLookupFailed(profile: GameProfile, exception: Exception) {}
            })
            newOnline = uuid.get()
        }

        if (newName == null && newOnline != null) {
            newName = sessionService.fillProfileProperties(GameProfile(UUID.fromString(newOnline), null), false).name
        }

        if (newName != null) {
            newOffline = Uuids.getOfflinePlayerUuid(newName).toString()
        }

        return Player(newName, newOnline, newOffline)
    }
}