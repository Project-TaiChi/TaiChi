package org.taichi.accessories.dark;

import org.taichi.accessories.AccessoryType;
import org.taichi.accessories.BasicTaiAccessory;

public abstract class BasicDarkAccessory extends BasicTaiAccessory {
    public BasicDarkAccessory(int tier) {
        super(AccessoryType.DARK, tier);
    }
}
