package org.taichi.data.provider.lang;

import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.neoforged.neoforge.common.data.LanguageProvider;
import org.taichi.TaiChiMod;
import org.taichi.curios.TaiCurioEffectModifier;
import org.taichi.curios.TaiCurioEffectType;
import org.taichi.data.provider.ItemDataContext;
import org.taichi.init.TaiAttributes;
import org.taichi.init.TaiCurioEffects;

public class ChineseLanguageProvider extends LanguageProvider {
    public ChineseLanguageProvider(PackOutput output) {
        super(output, TaiChiMod.MOD_ID, "zh_cn");
    }


    @Override
    protected void addTranslations() {
        for (ItemDataContext.ItemData itemData : ItemDataContext.ITEM_DATAS) {
            if (itemData.chineseTranslation() == null)
                continue;
            addItem(itemData.itemSupplier(), itemData.chineseTranslation());
        }

        addAttribute(TaiAttributes.MAGIC_ATTACK_DAMAGE_RATIO, "魔法伤害");


        addCurioEffectModifier("lighting", "雷击伤害");
        addCurioEffectModifier("freeze", "冻结伤害");
        addCurioEffectModifier("fall", "摔落伤害");
        addCurioEffectModifier("fall_and_vector", "摔落、矢量伤害");

        addCurioEffectType(TaiCurioEffects.ATTACKER_REACTION, "在夜晚高亮周围对你拥有敌意的生物");
        addCurioEffectType(TaiCurioEffects.MOON_LIGHT_VISION, "在夜晚拥有更好的视野");
        addCurioEffectType(TaiCurioEffects.CURING_ON_DYING, "当生命低于10%吊坠将碎裂并为佩戴者恢复所有生命值");
        addCurioEffectType(TaiCurioEffects.DAMAGE_IMMUNE, "免疫%s");
        addCurioEffectType(TaiCurioEffects.BREAK_ON_DAMAGE, "在受到%s时碎裂");
        addCurioEffectType(TaiCurioEffects.DOUBLE_JUMP, "为佩戴者提供二段跳效果");
        addCurioEffectType(TaiCurioEffects.MUTE_SOUND, "完全吸收佩戴者造成的所有声音");

    }


    private void addAttribute(Holder<Attribute> attribute, String translation) {
        add(attribute.value().getDescriptionId(), translation);
    }

    private void addCurioEffectType(Holder<TaiCurioEffectType<?>> effectType, String translation) {
        add(effectType.value().getTranslationKey(), translation);
    }

    private void addCurioEffectModifier(String name, String translation) {
        add(TaiCurioEffectModifier.translationKey(name), translation);
    }
}
