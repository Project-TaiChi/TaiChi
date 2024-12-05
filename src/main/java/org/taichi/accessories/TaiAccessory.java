package org.taichi.accessories;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public interface TaiAccessory extends ICurioItem {

    AccessoryType type();

    int tier();

    default int maxStackSize(ItemStack stack) {
        return 1;// 大部分饰品应该不可堆叠
    }

    @Override
    default boolean canEquip(SlotContext context, ItemStack stack) {
        LivingEntity entity = context.entity();
        if (!(entity instanceof Player player)) {
            return false;
        }
        return player.getData(TaiAttachments.PLAYER_ACCESSORY) == type();
    }
}
