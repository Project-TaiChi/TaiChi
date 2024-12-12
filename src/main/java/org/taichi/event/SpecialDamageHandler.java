package org.taichi.event;

import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import org.taichi.init.TaiAttributes;

@EventBusSubscriber
public class SpecialDamageHandler {

    @SubscribeEvent
    public static void onEntityDamage(LivingIncomingDamageEvent event) {

        if (event.getSource().getEntity() instanceof Player player) {
            handleSpecialDamage(player, event);

        }

    }


    private static void handleSpecialDamage(Player player, LivingIncomingDamageEvent event) {
        if (event.getSource().is(Tags.DamageTypes.IS_MAGIC)) {
            AttributeInstance magicInc = player.getAttribute(TaiAttributes.MAGIC_ATTACK_DAMAGE_RATIO);
            if (magicInc != null) {
                event.setAmount((float) (event.getAmount() * magicInc.getValue()));
            }

            AttributeInstance holyDamage = player.getAttribute(TaiAttributes.HOLY_ATTACK_DAMAGE);
            if (holyDamage != null && holyDamage.getValue() > 0) {
                handleHolyDamage(player, event, holyDamage.getBaseValue());
            }
        }

    }

    private static void handleHolyDamage(Player player, LivingIncomingDamageEvent event, double baseHolyDamage) {
        // TODO: 添加基于修正值的神圣伤害加成
        event.setAmount((float) (event.getAmount() + baseHolyDamage));
    }
}
