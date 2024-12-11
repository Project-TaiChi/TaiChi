package org.taichi.curios.type;

import net.minecraft.world.item.ItemStack;
import org.taichi.curios.TaiCurioEffectType;
import org.taichi.curios.effects.DamageImmuneEffectContext;
import top.theillusivec4.curios.api.SlotContext;

public class DamageImmuneEffect extends TaiCurioEffectType<DamageImmuneEffectContext> {
    public DamageImmuneEffect() {
        super(false, false);
    }

    @Override
    public DamageImmuneEffectContext create(ItemStack stack, SlotContext curioContext) {
        return new DamageImmuneEffectContext(this, stack, curioContext);
    }
}
