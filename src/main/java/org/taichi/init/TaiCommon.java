package org.taichi.init;

import net.minecraft.world.entity.EntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;

public final class TaiCommon {


    public static void init(IEventBus modbus) {
        TaiAttachments.init(modbus);
        TaiAttributes.init(modbus);
        TaiItems.init(modbus);
        TaiCreativeTabs.init(modbus);
        TaiCapabilities.init(modbus);
        TaiCurioEffects.init(modbus);

        modbus.addListener(TaiCommon::onAttributeCreation);
    }


    private static void onAttributeCreation(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, TaiAttributes.MAGIC_ATTACK_DAMAGE_RATIO);
    }





    private TaiCommon() {

    }
}
