package dcp.mc.pstp.api;

import net.minecraft.entity.Entity;

public interface Base <T extends Entity>{
    Class<T> getType();
}
