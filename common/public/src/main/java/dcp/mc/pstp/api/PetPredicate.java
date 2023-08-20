package dcp.mc.pstp.api;

import net.minecraft.entity.LivingEntity;

public interface PetPredicate<T extends LivingEntity> extends Base<T>{
    boolean isPet(T entity);
}
