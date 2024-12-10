package org.taichi.curios.effects;

import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.EffectCures;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingDamageEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import org.taichi.capability.TaiCurioEffectHandler;
import org.taichi.curios.ICuriosEffect;
import org.taichi.init.TaiCapabilities;
import org.taichi.init.TaiCurioEffects;
import org.taichi.utils.CuriosHelper;
import org.taichi.utils.EquippedCurio;

public class CuringOnDying implements ICuriosEffect {
    public CuringOnDying() {
        NeoForge.EVENT_BUS.addListener(this::onPlayerDamage);
        NeoForge.EVENT_BUS.addListener(this::onPlayerDeath);
    }


    private boolean tryCurePlayer(Player player) {
        TaiCurioEffectHandler effect = player.getCapability(TaiCapabilities.TAI_CURIO_EFFECT_HANDLER);
        if (effect == null)
            return false;
        if (!effect.hasEffect(TaiCurioEffects.CURING_ON_DYING.get()))
            return false;
        player.setHealth(player.getMaxHealth());
        player.removeEffectsCuredBy(EffectCures.PROTECTED_BY_TOTEM);

        EquippedCurio effectProvider = effect.findEffectProvider(TaiCurioEffects.CURING_ON_DYING.get());
        CuriosHelper.removeCurioFromPlayer(player, effectProvider);
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
    public boolean sync() {
        return false;
    }

    @Override
    public boolean persist() {
        return false;
    }

    @Override
    public boolean syncSelf() {
        return false;
    }
}
