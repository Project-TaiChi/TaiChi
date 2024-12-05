package org.taichi.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.taichi.ModConstants;
import org.taichi.utils.Keys;

import java.util.ArrayList;
import java.util.List;

public final class TaiCreativeTabs {

    private static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ModConstants.MOD_ID);


    private static List<DeferredItem<?>> lightEntries = new ArrayList<>();
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> lightTab = CREATIVE_TAB.register(
            "light_tab",
            () -> CreativeModeTab.builder()
                    .title(Keys.translatable("tab_light"))
                    .icon(ItemValues.moonPendant.asItem()::getDefaultInstance)
                    .displayItems((parameters, output) -> {
                        for (DeferredItem<?> creativeTagItem : lightEntries) {
                            output.accept(creativeTagItem.get());
                        }
                        lightEntries.clear();
                    })
                    .build()
    );

    private static List<DeferredItem<?>> darkEntries = new ArrayList<>();
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> darkTab = CREATIVE_TAB.register(
            "dark_tab",
            () -> CreativeModeTab.builder()
                    .title(Keys.translatable("tab_dark"))
                    .icon(Items.BARRIER::getDefaultInstance)
                    .displayItems((parameters, output) -> {
                        for (DeferredItem<?> creativeTagItem : darkEntries) {
                            output.accept(creativeTagItem.get());
                        }
                        darkEntries.clear();
                    })
                    .build()
    );

    private static List<DeferredItem<?>> ingredientEntries = new ArrayList<>();
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ingredientTab = CREATIVE_TAB.register(
            "ingredient_tab",
            () -> CreativeModeTab.builder()
                    .title(Keys.translatable("tab_ingredient"))
                    .icon(Items.BARRIER::getDefaultInstance)
                    .displayItems((parameters, output) -> {
                        for (DeferredItem<?> creativeTagItem : ingredientEntries) {
                            output.accept(creativeTagItem.get());
                        }
                        ingredientEntries.clear();
                    })
                    .build()
    );

    static void init(IEventBus modbus) {
        CREATIVE_TAB.register(modbus);
    }

    static void addLight(ItemValue<?> value) {
        lightEntries.add(value.getItem());
    }

    static void addDark(ItemValue<?> value) {
        darkEntries.add(value.getItem());
    }

    static void addIngredient(ItemValue<?> value) {
        ingredientEntries.add(value.getItem());
    }

}
