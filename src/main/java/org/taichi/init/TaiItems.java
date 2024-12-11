package org.taichi.init;

import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.checkerframework.checker.units.qual.A;
import org.taichi.TaiChiMod;
import org.taichi.item.TaiBaseItem;
import org.taichi.utils.builder.DamageTypeBuilder;
import org.taichi.utils.builder.TaiItemBuilder;

public final class TaiItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TaiChiMod.MOD_ID);

    public static final DeferredItem<TaiBaseItem> MOON_PENDANT = TaiItemBuilder.create("moon_pendant")
            .textChinese("幕月铃坠")
            .tag(CuriosTags.CHARM)
            .curio(builder -> builder
                    .withEffect(TaiCurioEffects.ATTACKER_REACTION)
                    .withEffect(TaiCurioEffects.MOON_LIGHT_VISION)
                    .withEffect(TaiCurioEffects.CURING_ON_DYING))
            .properties(properties -> properties.stacksTo(1))
            .tab(TaiTab.Curios)
            .attributes("curio", builder -> builder
                    .add(Attributes.LUCK, "luck", 1.0))
            .register();


    public static final DeferredItem<TaiBaseItem> GHOST_FILE = TaiItemBuilder.create("ghost_fire")
            .textChinese("祟灵厄火")
            .tag(CuriosTags.CURIO)
            .curio(builder -> builder
                    .withEffect(TaiCurioEffects.DAMAGE_IMMUNE, DamageTypeBuilder.create().type(DamageTypes.LIGHTNING_BOLT)::addToContext))

            .properties(properties -> properties.stacksTo(1))
            .tab(TaiTab.Ingredients)
            .attributes("curio", builder -> builder
                    .multiply(Attributes.MAX_HEALTH, "max_health", 0.05)
                    .multiply(Attributes.GRAVITY, "gravity", -0.2))
            .register();

    public static final DeferredItem<TaiBaseItem> RUNE_STONE = TaiItemBuilder.create("rune_stone")
            .textChinese("铭印符石")
            .tag(CuriosTags.CURIO)
            // TODO: 魔法伤害
            .properties(properties -> properties.stacksTo(1))
            .tab(TaiTab.Ingredients)
            .register();



    public static void init(IEventBus modbus) {
        ITEMS.register(modbus);
    }


}
