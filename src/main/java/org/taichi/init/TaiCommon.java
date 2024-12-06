package org.taichi.init;

import net.neoforged.bus.api.IEventBus;

public final class TaiCommon {


    public static void init(IEventBus modbus) {
        TaiAttachments.init(modbus);
        TaiItems.init(modbus);
        TaiCreativeTabs.init(modbus);
    }

    public static void registerAccessories() {
        TaiItems.registerAccessories();
    }

    private TaiCommon() {

    }
}
