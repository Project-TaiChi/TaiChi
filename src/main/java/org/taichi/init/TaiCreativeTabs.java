package org.taichi.init;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.taichi.TaiChiMod;
import org.taichi.utils.Keys;

import java.util.HashMap;
import java.util.function.Supplier;

public final class TaiCreativeTabs {
    private static final Multimap<String, Supplier<Item>> TAB_ITEMS = HashMultimap.create();

    public static void add(TaiTab tab, Supplier<Item> item) {
        TAB_ITEMS.put(tab.getName(), item);
    }


    private static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TaiChiMod.MOD_ID);

    private static Holder<CreativeModeTab> createTab(TaiTab tab, Supplier<Supplier<? extends Item>> icon) {
        return CREATIVE_TAB.register(
                tab.getName(),
                () -> CreativeModeTab.builder()
                        .title(Keys.translatable("tab_" + tab.getName()))
                        .icon(icon.get().get()::getDefaultInstance)
                        .displayItems((parameters, output) -> {
                            for (var item : TAB_ITEMS.get(tab.getName())) {
                                output.accept(item.get());
                            }
                        })
                        .build()
        );
    }

    public static final Holder<CreativeModeTab> CURIOS = createTab(TaiTab.Curios, () -> TaiItems.MOON_PENDANT);
    public static final Holder<CreativeModeTab> INGREDIENTS = createTab(TaiTab.Ingredients, () -> () -> Items.DIAMOND);

    static void init(IEventBus modbus) {
        CREATIVE_TAB.register(modbus);
    }

}
