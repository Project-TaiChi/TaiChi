package org.taichi.utils;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

public final class CuriosHelper {

    public static void removeAccessoryFromPlayer(Player player, Item item) {
        removeAccessoryFromPlayer(player, item, false);
    }

    public static void removeAccessoryFromPlayer(Player player, Item item, boolean all) {
        CuriosApi.getCuriosInventory(player).ifPresent(inventory -> {
            if (all) {
                for (SlotResult slot : inventory.findCurios(item)) {
                    ICurioStacksHandler stacksHandler = inventory.getCurios().get(slot.slotContext().identifier());
                    if (stacksHandler != null) {
                        stacksHandler.getStacks().setStackInSlot(slot.slotContext().index(), ItemStack.EMPTY);
                    }
                }
            } else {
                inventory.findFirstCurio(item).ifPresent(slot -> {
                    ICurioStacksHandler stacksHandler = inventory.getCurios().get(slot.slotContext().identifier());
                    if (stacksHandler != null) {
                        stacksHandler.getStacks().setStackInSlot(slot.slotContext().index(), ItemStack.EMPTY);
                    }
                });
            }
        });
    }

    private CuriosHelper() {
    }
}
