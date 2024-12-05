package org.taichi.data.provider;

import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import org.taichi.TaiChiMod;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class TaiDataPackProvider extends DatapackBuiltinEntriesProvider {

    public TaiDataPackProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, new RegistrySetBuilder(), Set.of(TaiChiMod.MOD_ID));
    }
}
