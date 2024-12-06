package org.taichi.accessories;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurio;

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

    @Override
    public @NotNull ICurio.DropRule getDropRule(SlotContext slotContext, DamageSource source, int lootingLevel, boolean recentlyHit, ItemStack stack) {
        return ICurio.DropRule.ALWAYS_KEEP;
    }
}
