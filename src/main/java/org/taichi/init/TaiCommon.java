package org.taichi.init;

import net.neoforged.bus.api.IEventBus;

public final class TaiCommon {


    public static void init(IEventBus modbus) {
        TaiAttachments.init(modbus);
        TaiItems.init(modbus);
        TaiCreativeTabs.init(modbus);
        TaiCapabilities.init(modbus);
        TaiCurioEffects.init(modbus);
    }


    private TaiCommon() {

    }
}
