package org.taichi.curios.effects;

import net.minecraft.world.item.ItemStack;
import org.taichi.curios.TaiCurioEffectContext;
import org.taichi.curios.TaiCurioEffectType;
import top.theillusivec4.curios.api.SlotContext;

public class DamageImmuneEffectContext extends TaiCurioEffectContext {
    public DamageImmuneEffectContext(TaiCurioEffectType<?> type, ItemStack stack, SlotContext curioContext) {
        super(type, stack, curioContext);
    }
}
