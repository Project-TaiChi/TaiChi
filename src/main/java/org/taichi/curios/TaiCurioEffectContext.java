package org.taichi.curios;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import org.taichi.init.TaiCurioEffects;
import top.theillusivec4.curios.api.SlotContext;

public class TaiCurioEffectContext {

    private final ItemStack stack;
    private final SlotContext curioContext;
    private TaiCurioEffectType<?> type;
    private boolean activated;

    public TaiCurioEffectContext(TaiCurioEffectType<?> type, ItemStack stack, SlotContext curioContext) {
        this.stack = stack;
        this.curioContext = curioContext;
        this.type = type;
        this.activated = true;
    }

    public ItemStack getStack() {
        return stack;
    }

    public SlotContext getSlotContext() {
        return curioContext;
    }

    public TaiCurioEffectType<? extends TaiCurioEffectContext> getType() {
        return type;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isActivated() {
        return activated;
    }

}
