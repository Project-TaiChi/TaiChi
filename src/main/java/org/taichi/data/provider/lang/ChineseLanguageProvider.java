package org.taichi.data.provider.lang;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.taichi.TaiChiMod;
import org.taichi.init.TaiItems;

public class ChineseLanguageProvider extends LanguageProvider {
    public ChineseLanguageProvider(PackOutput output) {
        super(output, TaiChiMod.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        this.add(TaiItems.moonPendant.asItem(), "幕月铃坠");
    }
}
