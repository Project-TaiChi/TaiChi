package org.taichi.data.provider;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.DamageTypeTagsProvider;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageTypes;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import org.taichi.TaiChiMod;
import org.taichi.init.TaiTags;

import java.util.concurrent.CompletableFuture;

public class TaiDamageTypeTagsProvider extends DamageTypeTagsProvider {
    public TaiDamageTypeTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, TaiChiMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.tag(TaiTags.DamageTypes.IS_VECTOR)
                .addTag(DamageTypeTags.IS_FALL)
                .add(DamageTypes.FLY_INTO_WALL);

        this.tag(TaiTags.DamageTypes.IS_STAR_BRACELET_IMMUNE)
                .addTag(DamageTypeTags.IS_FIRE)
                .addTag(DamageTypeTags.IS_DROWNING)
                .add(DamageTypes.IN_WALL)
                .addTag(DamageTypeTags.IS_FALL);
    }
}
