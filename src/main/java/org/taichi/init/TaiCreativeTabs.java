package org.taichi.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.taichi.ModConstants;
import org.taichi.utils.Keys;

public final class TaiCreativeTabs {

    private static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ModConstants.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> lightTab = CREATIVE_TAB.register(
            "light_tab",
            () -> CreativeModeTab.builder()
                    .title(Keys.translatable("tab_light"))
                    .icon(ItemValues.moonPendant.asItem()::getDefaultInstance)
                    .displayItems((parameters, output) -> {
                        for (var value : ItemValues.lightAccessories) {
                            output.accept(value.getItem().get());
                        }
                    })
                    .build()
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> darkTab = CREATIVE_TAB.register(
            "dark_tab",
            () -> CreativeModeTab.builder()
                    .title(Keys.translatable("tab_dark"))
                    .icon(Items.BARRIER::getDefaultInstance)
                    .displayItems((parameters, output) -> {
                        for (var value : ItemValues.darkAccessories) {
                            output.accept(value.getItem().get());
                        }
                    })
                    .build()
    );

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> ingredientTab = CREATIVE_TAB.register(
            "ingredient_tab",
            () -> CreativeModeTab.builder()
                    .title(Keys.translatable("tab_ingredient"))
                    .icon(Items.BARRIER::getDefaultInstance)
                    .displayItems((parameters, output) -> {
                        for (var value : ItemValues.ingredients) {
                            output.accept(value.getItem().get());
                        }
                    })
                    .build()
    );

    static void init(IEventBus modbus) {
        CREATIVE_TAB.register(modbus);
    }

}
