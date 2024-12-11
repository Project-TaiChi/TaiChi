package org.taichi.curios.type;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.EffectCures;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import org.taichi.capability.TaiCurioEffectHandler;
import org.taichi.curios.TaiCurioEffectContext;
import org.taichi.curios.TaiCurioEffectType;
import org.taichi.init.TaiCapabilities;
import org.taichi.init.TaiCurioEffects;
import org.taichi.utils.CuriosHelper;
import top.theillusivec4.curios.api.SlotContext;

public class CuringOnDying extends TaiCurioEffectType<TaiCurioEffectContext> {
    public CuringOnDying() {
        super(false, false);
        NeoForge.EVENT_BUS.addListener(this::onPlayerDamage);
        NeoForge.EVENT_BUS.addListener(this::onPlayerDeath);
    }


    private boolean tryCurePlayer(Player player) {
        TaiCurioEffectHandler effect = player.getCapability(TaiCapabilities.TAI_CURIO_EFFECT_HANDLER);
        if (effect == null)
            return false;

        TaiCurioEffectContext effectContext = effect.findFirstEffect(TaiCurioEffects.CURING_ON_DYING.get());
        if (effectContext == null) return false;

        player.setHealth(player.getMaxHealth());
        player.removeEffectsCuredBy(EffectCures.PROTECTED_BY_TOTEM);

        CuriosHelper.removeCurioFromPlayer(player, effectContext.getSlotContext(), effectContext.getStack());
        return true;
    }

    private void onPlayerDamage(LivingDamageEvent.Post event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) return;
        float percent = player.getHealth() / player.getMaxHealth();
        if (percent < 0.1) {
            tryCurePlayer(player);
        }
    }


    private void onPlayerDeath(LivingDeathEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;
        if (event.getSource().is(DamageTypeTags.BYPASSES_INVULNERABILITY)) return;

        if (tryCurePlayer(player)) {
            event.setCanceled(true);
        }

    }

    @Override
    public TaiCurioEffectContext create(ItemStack stack, SlotContext curioContext) {
        return new TaiCurioEffectContext(TaiCurioEffects.CURING_ON_DYING.get(), stack, curioContext);
    }
}
