package org.taichi.data.provider;

import com.google.common.collect.Iterables;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.taichi.TaiChiMod;
import org.taichi.init.TaiItems;

public class TaiItemModelProvider extends ItemModelProvider {

    public TaiItemModelProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, TaiChiMod.MOD_ID, helper);
    }

    @Override
    protected void registerModels() {

        for(ItemDataContext.ItemData itemData : ItemDataContext.ITEM_DATAS) {
            Item item = itemData.itemSupplier().get();
            this.basicItem(item);
        }

    }


}
