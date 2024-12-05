package org.taichi.data.provider.lang;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.taichi.TaiChiMod;

public class EnglishLanguageProvider extends LanguageProvider {
    public EnglishLanguageProvider(PackOutput output) {
        super(output, TaiChiMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {

    }
}
