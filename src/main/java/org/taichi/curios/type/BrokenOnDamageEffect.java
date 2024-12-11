package org.taichi.curios.type;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import org.taichi.curios.TaiCurioEffectType;
import org.taichi.curios.effects.DamageAwareEffectContext;
import org.taichi.init.TaiCurioEffects;
import org.taichi.utils.CapabilityHelper;
import org.taichi.utils.CuriosHelper;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class BrokenOnDamageEffect extends TaiCurioEffectType<DamageAwareEffectContext> {

    public BrokenOnDamageEffect() {
        super(false, false);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOW, this::onPlayerDamage);
    }

    @Override
    public DamageAwareEffectContext create(ItemStack stack, SlotContext curioContext) {
        return new DamageAwareEffectContext(this, stack, curioContext);
    }

    private void onPlayerDamage(LivingDamageEvent.Post event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (event.getNewDamage() > 0) {
            List<DamageAwareEffectContext> contexts = CapabilityHelper.findEntityEffectContexts(player, TaiCurioEffects.BREAK_ON_DAMAGE.get());

            for (DamageAwareEffectContext context : contexts) {
                if (context.test(event.getSource())) {
                    CuriosHelper.removeCurioFromPlayer(player, context.getSlotContext(), context.getStack());
                }
            }
        }
    }


}
