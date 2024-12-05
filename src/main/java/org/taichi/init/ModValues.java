package org.taichi.init;

import net.neoforged.bus.api.IEventBus;

public final class ModValues {


    public static void init(IEventBus modbus) {
        ItemValues.init(modbus);
        TaiCreativeTabs.init(modbus);
    }

    public static void registerAccessories() {
        ItemValues.registerAccessories();
    }

    private ModValues() {

    }
}
