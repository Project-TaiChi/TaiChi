package org.taichi.init;

import net.minecraft.world.item.Item;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.taichi.ModConstants;
import org.taichi.accessories.AccessoryType;
import org.taichi.accessories.TaiAccessory;
import org.taichi.accessories.TaiAccessoryItem;
import org.taichi.accessories.light.MoonPendant;
import top.theillusivec4.curios.api.CuriosApi;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public final class ItemValues {

    private static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ModConstants.MOD_ID);

    private static final List<ItemValue<TaiAccessoryItem>> accessories = new ArrayList<>();

    public static final ItemValue<TaiAccessoryItem> moonPendant = accessory("moon_pendant", new MoonPendant());

    private static ItemValue<TaiAccessoryItem> accessory(String name, TaiAccessory accessory) {
        ItemValue<TaiAccessoryItem> itemValue = new ItemValue<>(ITEMS.register(name, () -> new TaiAccessoryItem(new Item.Properties(), accessory)));
        accessories.add(itemValue);
        if (accessory.type() == AccessoryType.LIGHT) {
            TaiCreativeTabs.addLight(itemValue);
        } else {
            TaiCreativeTabs.addDark(itemValue);
        }
        return itemValue;
    }

    private static <T extends Item> ItemValue<T> item(String name, Supplier<T> supplier) {
        return new ItemValue<>(ITEMS.register(name, supplier));
    }

    static void init(IEventBus modbus) {
        ITEMS.register(modbus);
    }

    static void registerAccessories() {
        for (var item : accessories) {
            var value = item.getItem().value();
            CuriosApi.registerCurio(value, value.accessory());
        }
    }

    private ItemValues() {

    }
}
