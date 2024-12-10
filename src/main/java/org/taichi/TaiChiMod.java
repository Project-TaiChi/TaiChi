package org.taichi;

import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import org.taichi.init.TaiCommon;

import java.util.Locale;


@Mod(TaiChiMod.MOD_ID)
public class TaiChiMod {
    public static final String MOD_ID = "tai_chi";
    private static final Logger LOGGER = LogUtils.getLogger();

    public TaiChiMod(IEventBus modEventBus, ModContainer modContainer) {
        TaiCommon.init(modEventBus);
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {

    }

    //TODO:a better name
    public static ResourceLocation loc(String name){
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, name.toLowerCase(Locale.ROOT));
    }
}
