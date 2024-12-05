package org.taichi.data.provider;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.taichi.TaiChiMod;
import top.theillusivec4.curios.api.CuriosDataProvider;

import java.util.concurrent.CompletableFuture;

public class TaiCuriosProvider extends CuriosDataProvider {
    public TaiCuriosProvider(PackOutput output, ExistingFileHelper fileHelper, CompletableFuture<HolderLookup.Provider> registries) {
        super(TaiChiMod.MOD_ID, output, fileHelper, registries);
    }

    @Override
    public void generate(HolderLookup.Provider registries, ExistingFileHelper fileHelper) {

        this.createEntities("tai_player")
                .addPlayer()
                .addSlots("back", "belt", "body", "bracelet", "charm", "curio", "hands", "head", "necklace", "ring");
    }
}
