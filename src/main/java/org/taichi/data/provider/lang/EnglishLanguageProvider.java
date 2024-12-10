package org.taichi.data.provider.lang;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.taichi.TaiChiMod;
import org.taichi.data.provider.ItemDataContext;

public class EnglishLanguageProvider extends LanguageProvider {
    public EnglishLanguageProvider(PackOutput output) {
        super(output, TaiChiMod.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {

        for (ItemDataContext.ItemData itemData : ItemDataContext.ITEM_DATAS) {
            if (itemData.englishTranslation() == null)
                continue;
            addItem(itemData.itemSupplier(), itemData.englishTranslation());
        }
    }
}
