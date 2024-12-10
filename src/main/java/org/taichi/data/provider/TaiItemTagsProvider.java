package org.taichi.data.provider;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.taichi.TaiChiMod;

import java.util.concurrent.CompletableFuture;

public class TaiItemTagsProvider extends ItemTagsProvider {
    public TaiItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, TaiChiMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        for (ItemDataContext.ItemData itemData : ItemDataContext.ITEM_DATAS) {
            Item item = itemData.itemSupplier().get();
            for (TagKey<Item> tag : itemData.tags()) {
                this.tag(tag).add(item);
            }
        }

    }
}
