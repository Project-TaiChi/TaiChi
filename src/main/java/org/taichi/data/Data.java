package org.taichi.data;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import org.taichi.TaiChiMod;
import org.taichi.data.provider.*;
import org.taichi.data.provider.lang.ChineseLanguageProvider;
import org.taichi.data.provider.lang.EnglishLanguageProvider;

@EventBusSubscriber(modid = TaiChiMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Data {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        var gen = event.getGenerator();
        var packOutput = gen.getPackOutput();
        var helper = event.getExistingFileHelper();
        var lookupProvider = event.getLookupProvider();

        gen.addProvider(event.includeClient(), new EnglishLanguageProvider(packOutput));
        gen.addProvider(event.includeClient(), new ChineseLanguageProvider(packOutput));

        gen.addProvider(event.includeClient(), new TaiItemModelProvider(packOutput, helper));
        gen.addProvider(event.includeClient(), new TaiBlockStateProvider(packOutput, helper));
        gen.addProvider(event.includeServer(), new TaiRecipeProvider(packOutput, lookupProvider));
        gen.addProvider(event.includeServer(), new TaiDataPackProvider(packOutput, lookupProvider));
        gen.addProvider(event.includeServer(), new TaiLootTableProvider(packOutput, lookupProvider));

        TaiBlockTagsProvider blockTagsProvider = new TaiBlockTagsProvider(packOutput, lookupProvider, helper);
        gen.addProvider(event.includeServer(), blockTagsProvider);
        gen.addProvider(event.includeServer(), new TaiItemTagsProvider(packOutput, lookupProvider, blockTagsProvider.contentsGetter(), helper));

        gen.addProvider(event.includeServer(), new TaiCuriosProvider(packOutput, helper, lookupProvider));

        gen.addProvider(event.includeServer(), new TaiDamageTypeTagsProvider(packOutput, lookupProvider, helper));

    }
}
