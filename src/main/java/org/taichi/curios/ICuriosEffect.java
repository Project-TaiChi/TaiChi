package org.taichi.curios;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public interface ICuriosEffect {

    boolean sync();

    boolean persist();

    boolean syncSelf();

    default void effectAdded(LivingEntity entity, ItemStack stack) {
    }

    default void effectRemoved(LivingEntity entity, ItemStack stack) {
    }

}
