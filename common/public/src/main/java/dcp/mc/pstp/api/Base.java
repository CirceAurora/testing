package dcp.mc.pstp.api;

import net.minecraft.entity.LivingEntity;

public interface Base <T extends LivingEntity>{
    Class<T> getType();
}
