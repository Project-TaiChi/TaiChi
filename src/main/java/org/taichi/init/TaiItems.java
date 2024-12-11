package org.taichi.init;

import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.taichi.TaiChiMod;
import org.taichi.item.TaiBaseItem;
import org.taichi.utils.builder.TaiItemBuilder;

public final class TaiItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TaiChiMod.MOD_ID);

    public static final DeferredItem<TaiBaseItem> MOON_PENDANT = TaiItemBuilder.create("moon_pendant")
            .textChinese("幕月铃坠")
            .tag(CuriosTags.CURIO)
            .curio(builder -> builder
                    .withEffect(TaiCurioEffects.ATTACKER_REACTION)
                    .withEffect(TaiCurioEffects.MOON_LIGHT_VISION)
                    .withEffect(TaiCurioEffects.CURING_ON_DYING))
            .properties(properties -> properties.stacksTo(1))
            .tab(TaiTab.Curios)
            .attributes("curio", builder -> builder
                    .add(Attributes.LUCK, "luck", 1.0))
            .register();


    public static void init(IEventBus modbus) {
        ITEMS.register(modbus);
    }


}
