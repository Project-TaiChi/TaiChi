package org.taichi.data.provider.lang;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.taichi.TaiChiMod;
import org.taichi.init.TaiItem;

public class ChineseLanguageProvider extends LanguageProvider {
    public ChineseLanguageProvider(PackOutput output) {
        super(output, TaiChiMod.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {
        this.add(TaiItem.moonPendant.asItem(), "幕月铃坠");
    }
}
