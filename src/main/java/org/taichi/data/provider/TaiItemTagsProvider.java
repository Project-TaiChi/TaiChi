package org.taichi.data.provider;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.taichi.TaiChiMod;
import org.taichi.init.CuriosTags;
import org.taichi.init.TaiItems;

import java.util.concurrent.CompletableFuture;

public class TaiItemTagsProvider extends ItemTagsProvider {
    public TaiItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, CompletableFuture<TagLookup<Block>> blockTags, ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, blockTags, TaiChiMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {

        this.tag(CuriosTags.NECKLACE)
                .add(TaiItems.moonPendant.asItem());
    }
}
