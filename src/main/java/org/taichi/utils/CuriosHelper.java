package org.taichi.utils;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.taichi.capability.TaiCurioEffectHandler;
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


    public static boolean removeCurioFromPlayer(Player player, EquippedCurio curio) {
        if (curio == null) {
            return false;
        }
        return CuriosApi.getCuriosInventory(player).map(inventory -> {
            return inventory.getStacksHandler(curio.slotType()).map(stacksHandler -> {
                if (stacksHandler.getSlots() <= curio.slotId()) {
                    return false;
                }
                ItemStack existingStack = stacksHandler.getStacks().getStackInSlot(curio.slotId());
                if (ItemStack.isSameItemSameComponents(existingStack, curio.stack())) {
                    stacksHandler.getStacks().setStackInSlot(curio.slotId(), ItemStack.EMPTY);
                    return true;
                }
                return false;
            }).orElse(false);
        }).orElse(false);
    }

    private CuriosHelper() {
    }
}
