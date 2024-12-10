package org.taichi.data.provider.lang;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.taichi.TaiChiMod;
import org.taichi.data.provider.ItemDataContext;

public class ChineseLanguageProvider extends LanguageProvider {
    public ChineseLanguageProvider(PackOutput output) {
        super(output, TaiChiMod.MOD_ID, "zh_cn");
    }


    @Override
    protected void addTranslations() {
        for (ItemDataContext.ItemData itemData : ItemDataContext.ITEM_DATAS) {
            if(itemData.chineseTranslation() == null)
                continue;
            addItem(itemData.itemSupplier(), itemData.chineseTranslation());
        }

    }
}
