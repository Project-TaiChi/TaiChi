package org.taichi.init;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.taichi.TaiChiMod;
import org.taichi.accessories.AccessoryType;
import org.taichi.accessories.TaiAccessory;
import org.taichi.accessories.TaiAccessoryItem;
import org.taichi.accessories.light.MoonPendant;
import org.taichi.entry.ItemValue;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.function.Supplier;

public final class TaiItems {

    public static final DeferredRegister.Items commonEntries = DeferredRegister.createItems(TaiChiMod.MOD_ID);
    public static final DeferredRegister.Items lightEntries = DeferredRegister.createItems(TaiChiMod.MOD_ID);
    public static final DeferredRegister.Items darkEntries = DeferredRegister.createItems(TaiChiMod.MOD_ID);
    public static final DeferredRegister.Items ingredientEntries = DeferredRegister.createItems(TaiChiMod.MOD_ID);

    public static final ItemValue<TaiAccessoryItem> moonPendant = accessory("moon_pendant", new MoonPendant());

    private static ItemValue<TaiAccessoryItem> accessory(String name, TaiAccessory accessory) {
        if (accessory.type() == AccessoryType.LIGHT) {
            return new ItemValue<>(lightEntries.register(name,()->  new TaiAccessoryItem(new Item.Properties(), accessory)));
        }
        if (accessory.type() == AccessoryType.DARK) {
            return new ItemValue<>(darkEntries.register(name,()->  new TaiAccessoryItem(new Item.Properties(), accessory)));
        }
        return new ItemValue<>(commonEntries.register(name,()->  new TaiAccessoryItem(new Item.Properties(), accessory)));
    }

    private static <T extends Item> ItemValue<T> ingredient(String name, Supplier<T> supplier) {
        return new ItemValue<>(ingredientEntries.register(name, supplier));
    }

    private static <T extends Item> ItemValue<T> common(String name, Supplier<T> supplier) {
        return new ItemValue<>(commonEntries.register(name, supplier));
    }

    static void init(IEventBus modbus) {
        commonEntries.register(modbus);
        lightEntries.register(modbus);
        darkEntries.register(modbus);
        ingredientEntries.register(modbus);
    }

    static void registerAccessories() {
        for (var item : lightEntries.getEntries()) {
            var value = item.get().asItem();
            if(value instanceof TaiAccessoryItem taiAccessoryItem) {
                CuriosApi.registerCurio(value, taiAccessoryItem.accessory());
            }
        }
        for (var item : darkEntries.getEntries()) {
            var value = item.get().asItem();
            if(value instanceof TaiAccessoryItem taiAccessoryItem) {
                CuriosApi.registerCurio(value, taiAccessoryItem.accessory());
            }
        }
    }
}
