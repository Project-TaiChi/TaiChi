package org.taichi.data.provider;

import com.google.common.collect.Iterables;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.taichi.TaiChiMod;
import org.taichi.init.TaiBlock;
import org.taichi.init.TaiItem;

import java.util.stream.Collectors;

public class TaiItemModelProvider extends ItemModelProvider {

    public TaiItemModelProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, TaiChiMod.MOD_ID, helper);
    }

    @Override
    protected void registerModels() {

        this.basicItem(TaiItem.moonPendant.asItem());

    }


    private static Iterable<Item> getCommonItems() {
        return Iterables.transform(TaiItem.commonEntries.getEntries(), DeferredHolder::get);
    }
    private static Iterable<Item> getLightItems() {
        return Iterables.transform(TaiItem.lightEntries.getEntries(), DeferredHolder::get);
    }
    private static Iterable<Item> getDarkItems() {
        return Iterables.transform(TaiItem.darkEntries.getEntries(), DeferredHolder::get);
    }
    private static Iterable<Item> getIngredientItems() {
        return Iterables.transform(TaiItem.ingredientEntries.getEntries(), DeferredHolder::get);
    }
}
