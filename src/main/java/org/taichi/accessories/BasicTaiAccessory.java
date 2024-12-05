package org.taichi.accessories;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public abstract class BasicTaiAccessory implements TaiAccessory {

    private final AccessoryType type;
    private final int tier;
    protected int tickCounter = 0;

    public BasicTaiAccessory(AccessoryType type, int tier) {
        this.type = type;
        this.tier = tier;
    }

    @Override
    public AccessoryType type() {
        return type;
    }

    @Override
    public int tier() {
        return tier;
    }

    public int tickRate() {
        return 20;
    }

    @Override
    public void curioTick(SlotContext context, ItemStack stack) {
        if (!(context.entity() instanceof Player player)) return;
        tickCounter++;
        if (tickCounter % tickRate() == 0) {
            tickInternal(stack, context, player);
        }
    }

    protected void tickInternal(ItemStack stack, SlotContext context, Player player) {
    }
}
