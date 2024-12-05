package org.taichi.accessories;

import net.minecraft.world.item.Item;

public class TaiAccessoryItem extends Item {

    private final TaiAccessory accessory;

    public TaiAccessoryItem(Properties properties, TaiAccessory accessory) {
        super(properties);
        this.accessory = accessory;
    }

    public TaiAccessory accessory() {
        return accessory;
    }
}
