package org.taichi.utils.builder;

import com.google.common.collect.Lists;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import org.taichi.curios.effects.DamageImmuneEffectContext;

import java.util.List;

public class DamageTypeBuilder {
    private final List<TagKey<DamageType>> tags;
    private final List<ResourceKey<DamageType>> keys;

    public static DamageTypeBuilder create() {
        return new DamageTypeBuilder();
    }

    public DamageTypeBuilder() {
        this.tags = Lists.newArrayList();
        this.keys = Lists.newArrayList();
    }

    public DamageTypeBuilder tag(TagKey<DamageType> tag) {
        this.tags.add(tag);
        return this;
    }

    public DamageTypeBuilder type(ResourceKey<DamageType> key) {
        this.keys.add(key);
        return this;
    }

    public void addToContext(DamageImmuneEffectContext context) {
        if (!tags.isEmpty()) {
            context.setImmuneDamageTags(tags);
        }
        if (!keys.isEmpty()) {
            context.setImmuneDamageTypes(keys);
        }
    }


}
