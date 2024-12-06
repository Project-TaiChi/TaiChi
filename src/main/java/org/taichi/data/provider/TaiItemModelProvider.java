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

        this.basicItem(TaiItems.moonPendant.asItem());

    }


    private static Iterable<Item> getCommonItems() {
        return Iterables.transform(TaiItems.commonEntries.getEntries(), DeferredHolder::get);
    }
    private static Iterable<Item> getLightItems() {
        return Iterables.transform(TaiItems.lightEntries.getEntries(), DeferredHolder::get);
    }
    private static Iterable<Item> getDarkItems() {
        return Iterables.transform(TaiItems.darkEntries.getEntries(), DeferredHolder::get);
    }
    private static Iterable<Item> getIngredientItems() {
        return Iterables.transform(TaiItems.ingredientEntries.getEntries(), DeferredHolder::get);
    }
}
