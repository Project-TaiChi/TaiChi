package org.taichi.curios;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public interface IContextCurioEffect<T> extends ICuriosEffect {
    T createContext(SlotContext slotContext, ItemStack stack);
}
