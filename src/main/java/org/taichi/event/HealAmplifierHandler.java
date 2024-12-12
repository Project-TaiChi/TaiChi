package org.taichi.event;

import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import org.taichi.init.TaiAttributes;

@EventBusSubscriber
public class HealAmplifierHandler {

    @SubscribeEvent
    public static void onHeal(LivingHealEvent event) {
        AttributeInstance healingAmplifier = event.getEntity().getAttribute(TaiAttributes.HEALING_AMPLIFIER);
        if (healingAmplifier != null) {
            event.setAmount((float) (event.getAmount() * healingAmplifier.getValue()));
        }
    }
}
