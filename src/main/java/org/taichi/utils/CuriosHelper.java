package org.taichi.utils;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.taichi.capability.TaiCurioEffectHandler;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.SlotResult;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;

public final class CuriosHelper {

    public static void removeCurioFromPlayer(Player player, Item item) {
        removeCurioFromPlayer(player, item, false);
    }

    public static void removeCurioFromPlayer(Player player, Item item, boolean all) {
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


    public static boolean removeCurioFromPlayer(Player player, SlotContext slot, ItemStack stack) {
        return CuriosApi.getCuriosInventory(player).map(inventory -> {
            return inventory.getStacksHandler(slot.identifier()).map(stacksHandler -> {
                if (stacksHandler.getSlots() <= slot.index()) {
                    return false;
                }
                ItemStack existingStack = stacksHandler.getStacks().getStackInSlot(slot.index());
                if (ItemStack.isSameItemSameComponents(existingStack, stack)) {
                    stacksHandler.getStacks().setStackInSlot(slot.index(), ItemStack.EMPTY);
                    return true;
                }
                return false;
            }).orElse(false);
        }).orElse(false);
    }

    private CuriosHelper() {
    }
}
