package org.taichi.init;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.PercentageAttribute;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.taichi.TaiChiMod;

public class TaiAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(Registries.ATTRIBUTE, TaiChiMod.MOD_ID);

    public static final Holder<Attribute> MAGIC_ATTACK_DAMAGE_RATIO = createPercentage("magic_attack_damage_ratio", 1.0D, 0.0, 100.0, 100.0);
    public static final Holder<Attribute> HOLY_ATTACK_DAMAGE = createRanged("holy_attack_damage", 0.0D, 0.0, 1024.0D);
    public static final Holder<Attribute> HEALING_AMPLIFIER = createPercentage("healing_amplifier", 1.0D, 0.0, 10.0, 100.0);
    public static final Holder<Attribute> POTION_DURATION_RATIO = createPercentage("potion_duration_ratio", 1.0D, 0.0, 100.0, 100.0);

    public static void init(IEventBus modbus) {
        ATTRIBUTES.register(modbus);
        modbus.addListener(TaiAttributes::onAttributeCreation);
    }

    private static String attributeId(String id) {
        return TaiChiMod.MOD_ID + ".attribute.name." + id;
    }

    private static Holder<Attribute> createRanged(String id, double defaultValue, double minValue, double maxValue) {
        return ATTRIBUTES.register(id, () -> new RangedAttribute(attributeId(id), defaultValue, minValue, maxValue));
    }

    private static Holder<Attribute> createPercentage(String id, double defaultValue, double minValue, double maxValue, double factor) {
        return ATTRIBUTES.register(id, () -> new PercentageAttribute(attributeId(id), defaultValue, minValue, maxValue, factor));
    }


    private static void onAttributeCreation(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, TaiAttributes.MAGIC_ATTACK_DAMAGE_RATIO);
        event.add(EntityType.PLAYER, TaiAttributes.HOLY_ATTACK_DAMAGE);
        event.add(EntityType.PLAYER, TaiAttributes.HEALING_AMPLIFIER);
        event.add(EntityType.PLAYER, TaiAttributes.POTION_DURATION_RATIO);
    }
}
