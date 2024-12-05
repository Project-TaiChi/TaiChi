package org.taichi.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.taichi.TaiChiMod;
import org.taichi.utils.Keys;

public final class TaiCreativeTab {

    private static final DeferredRegister<CreativeModeTab> CREATIVE_TAB = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, TaiChiMod.MOD_ID);


    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> lightTab = CREATIVE_TAB.register(
            "light_tab",
            () -> CreativeModeTab.builder()
                    .title(Keys.translatable("tab_light"))
                    .icon(TaiItem.moonPendant.asItem()::getDefaultInstance)
                    .displayItems((parameters, output) -> {
                        for (var creativeTagItem : TaiItem.lightEntries.getEntries()) {
                            output.accept(creativeTagItem.get());
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
                        for (var creativeTagItem : TaiItem.darkEntries.getEntries()) {
                            output.accept(creativeTagItem.get());
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
                        for (var creativeTagItem : TaiItem.ingredientEntries.getEntries()) {
                            output.accept(creativeTagItem.get());
                        }
                    })
                    .build()
    );

    static void init(IEventBus modbus) {
        CREATIVE_TAB.register(modbus);
    }

}
