package org.taichi.init;

import net.neoforged.bus.api.IEventBus;

public final class TaiCommon {


    public static void init(IEventBus modbus) {
        TaiAttachment.init(modbus);
        TaiItem.init(modbus);
        TaiCreativeTab.init(modbus);
    }

    public static void registerAccessories() {
        TaiItem.registerAccessories();
    }

    private TaiCommon() {

    }
}
