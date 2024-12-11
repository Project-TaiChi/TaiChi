package org.taichi.curios.type;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.EntityInvulnerabilityCheckEvent;
import org.taichi.curios.TaiCurioEffectType;
import org.taichi.curios.effects.DamageAwareEffectContext;
import org.taichi.init.TaiCurioEffects;
import org.taichi.utils.CapabilityHelper;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class DamageImmuneEffect extends TaiCurioEffectType<DamageAwareEffectContext> {
    public DamageImmuneEffect() {
        super(false, false);
        NeoForge.EVENT_BUS.addListener(this::onEntityInvulnerabilityCheck);
    }

    @Override
    public DamageAwareEffectContext create(ItemStack stack, SlotContext curioContext) {
        return new DamageAwareEffectContext(this, stack, curioContext);
    }


    private void onEntityInvulnerabilityCheck(EntityInvulnerabilityCheckEvent event) {
        if (!(event.getEntity() instanceof LivingEntity player)) return;

        List<DamageAwareEffectContext> contexts = CapabilityHelper.findEntityEffectContexts(player, TaiCurioEffects.DAMAGE_IMMUNE.get());

        for (DamageAwareEffectContext context : contexts) {
            if (context.test(event.getSource())) {
                event.setInvulnerable(true);
                return;
            }
        }

    }
}
