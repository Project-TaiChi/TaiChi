package org.taichi;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;
import org.taichi.accessories.TaiAttachments;
import org.taichi.init.ModValues;

import static org.taichi.ModConstants.MOD_ID;

@Mod(MOD_ID)
public class TaiChiMod {
    private static final Logger LOGGER = LogUtils.getLogger();

    public TaiChiMod(IEventBus modEventBus, ModContainer modContainer) {
        TaiAttachments.init(modEventBus);
        ModValues.init(modEventBus);
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(ModValues::registerAccessories);
    }

}
