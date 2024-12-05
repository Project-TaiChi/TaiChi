package org.taichi.data.provider;

import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.taichi.TaiChiMod;

public class TaiBlockStateProvider extends BlockStateProvider {
    public TaiBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, TaiChiMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

    }
}
