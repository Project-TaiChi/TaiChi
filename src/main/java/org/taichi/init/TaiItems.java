package org.taichi.init;

import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.taichi.TaiChiMod;
import org.taichi.curios.EffectModifiers;
import org.taichi.item.TaiBaseItem;
import org.taichi.utils.builder.TaiItemBuilder;

public final class TaiItems {

    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TaiChiMod.MOD_ID);

    public static final DeferredItem<TaiBaseItem> MOON_PENDANT = TaiItemBuilder.create("moon_pendant")
            .textChinese("幕月铃坠")
            .tag(TaiTags.CuriosTags.CHARM)
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
            .tag(TaiTags.CuriosTags.CURIO)
            .curio(builder -> builder
                    .withEffect(TaiCurioEffects.DAMAGE_IMMUNE, EffectModifiers.damageType().type(DamageTypes.LIGHTNING_BOLT).translationName("lighting"))

            )

            .properties(properties -> properties.stacksTo(1))
            .tab(TaiTab.Ingredients)
            .attributes("curio", builder -> builder
                    .multiply(Attributes.MAX_HEALTH, "max_health", 0.05)
                    .multiply(Attributes.GRAVITY, "gravity", -0.2))
            .register();

    public static final DeferredItem<TaiBaseItem> RUNE_STONE = TaiItemBuilder.create("rune_stone")
            .textChinese("铭印符石")
            .tag(TaiTags.CuriosTags.CURIO)
            .properties(properties -> properties.stacksTo(1))
            .tab(TaiTab.Ingredients)
            .attributes("curio", builder -> builder
                    .add(TaiAttributes.MAGIC_ATTACK_DAMAGE_RATIO, "max_health", 0.15))
            .register();

    public static final DeferredItem<TaiBaseItem> ALLAY_SPECIMEN = TaiItemBuilder.create("allay_specimen")
            .textChinese("静籁悦灵")
            .tag(TaiTags.CuriosTags.CURIO)
            .curio(builder -> builder
                    .withEffect(TaiCurioEffects.DAMAGE_IMMUNE, EffectModifiers.damageType().type(DamageTypes.FREEZE).translationName("freeze")))
            .properties(properties -> properties.stacksTo(1))
            .tab(TaiTab.Ingredients)
            .attributes("curio", builder -> builder
                    .multiply(Attributes.MOVEMENT_SPEED, "max_health", 0.1)
                    .add(Attributes.LUCK, "luck", 1.0))
            .register();


    public static final DeferredItem<TaiBaseItem> GRAVE_PIECE = TaiItemBuilder.create("grave_piece")
            .textChinese("盗掘墓片")
            .tag(TaiTags.CuriosTags.CURIO)
            .curio(builder -> builder
                    .withEffect(TaiCurioEffects.BREAK_ON_DAMAGE, EffectModifiers.damageType().type(DamageTypes.FALL).translationName("fall"))
                    .withEffect(TaiCurioEffects.MUTE_SOUND))
            .properties(properties -> properties.stacksTo(1))
            .tab(TaiTab.Ingredients)
            .attributes("curio", builder -> builder
                    .add(Attributes.ARMOR_TOUGHNESS, "armor_toughness", 6.0))
            .register();

    // 星界晶块
    public static final DeferredItem<TaiBaseItem> STAR_CRYSTAL = TaiItemBuilder.create("star_crystal")
            .textChinese("星界晶块")
            .tag(TaiTags.CuriosTags.CURIO)
            .curio(builder -> builder
                    .withEffect(TaiCurioEffects.DAMAGE_IMMUNE, EffectModifiers.damageType().tag(TaiTags.DamageTypes.IS_VECTOR).translationName("fall_and_vector"))
                    .withEffect(TaiCurioEffects.DOUBLE_JUMP))
            .properties(properties -> properties.stacksTo(1))
            .tab(TaiTab.Ingredients)
            .attributes("curio", builder -> builder
                    // movement speed has ratio of 1000, so 0.01 is 10%
                    .add(Attributes.MOVEMENT_SPEED, "movement_speed", 0.01))
            .register();

    public static void init(IEventBus modbus) {
        ITEMS.register(modbus);
    }


}
