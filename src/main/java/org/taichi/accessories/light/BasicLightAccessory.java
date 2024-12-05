package org.taichi.accessories.light;

import org.taichi.accessories.AccessoryType;
import org.taichi.accessories.BasicTaiAccessory;

public abstract class BasicLightAccessory extends BasicTaiAccessory {
    public BasicLightAccessory(int tier) {
        super(AccessoryType.LIGHT, tier);
    }
}
