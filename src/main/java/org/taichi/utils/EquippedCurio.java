package org.taichi.utils;


import net.minecraft.world.item.ItemStack;

public record EquippedCurio(ItemStack stack, String slotType, int slotId) {
}