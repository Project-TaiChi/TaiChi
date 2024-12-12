package org.taichi.event;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.MobEffectEvent;
import org.taichi.init.TaiAttributes;

@EventBusSubscriber
public class PotionDurationHandler {

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onEffectAdd(MobEffectEvent.Added event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        if (event.getEffectInstance().isInfiniteDuration()) {
            return;
        }
        MobEffectInstance instance = event.getEffectInstance();

        MobEffect effect = instance.getEffect().value();
        if (effect.isInstantenous() || effect.getCategory() != MobEffectCategory.BENEFICIAL) {
            return;
        }

        AttributeInstance effectRatio = player.getAttribute(TaiAttributes.POTION_DURATION_RATIO);

        if (effectRatio == null) {
            return;
        }

        double ratio = effectRatio.getValue();
        instance.duration = (int) Math.ceil(instance.getDuration() * ratio);


    }
}
