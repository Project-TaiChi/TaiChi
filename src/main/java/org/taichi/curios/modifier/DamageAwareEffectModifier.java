package org.taichi.curios.modifier;

import com.google.common.collect.Lists;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import org.taichi.TaiChiMod;
import org.taichi.curios.TaiCurioEffectModifier;
import org.taichi.curios.effects.DamageAwareEffectContext;

import java.util.List;

public class DamageAwareEffectModifier implements TaiCurioEffectModifier<DamageAwareEffectContext> {
    private final List<TagKey<DamageType>> tags;
    private final List<ResourceKey<DamageType>> keys;
    private String translationKey = TaiCurioEffectModifier.translationKey("unknown");


    public DamageAwareEffectModifier() {
        this.tags = Lists.newArrayList();
        this.keys = Lists.newArrayList();
    }

    public DamageAwareEffectModifier tag(TagKey<DamageType> tag) {
        this.tags.add(tag);
        return this;
    }

    public DamageAwareEffectModifier type(ResourceKey<DamageType> key) {
        this.keys.add(key);
        return this;
    }

    private void addToContext(DamageAwareEffectContext context) {
        if (!tags.isEmpty()) {
            context.setDamageTags(tags);
        }
        if (!keys.isEmpty()) {
            context.setDamageTypes(keys);
        }
    }

    public DamageAwareEffectModifier translationName(String key) {
        this.translationKey = TaiCurioEffectModifier.translationKey(key);
        return this;
    }


    @Override
    public void accept(DamageAwareEffectContext damageAwareEffectContext) {
        addToContext(damageAwareEffectContext);
    }

    @Override
    public Component getDesc() {
        return Component.translatable(translationKey).withStyle(ChatFormatting.GOLD);
    }
}
