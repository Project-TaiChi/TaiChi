package org.taichi.data.provider.lang;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.taichi.TaiChiMod;

public class ChineseLanguageProvider extends LanguageProvider {
    public ChineseLanguageProvider(PackOutput output) {
        super(output, TaiChiMod.MOD_ID, "zh_cn");
    }

    @Override
    protected void addTranslations() {

    }
}
