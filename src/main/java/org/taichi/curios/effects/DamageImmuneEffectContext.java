package org.taichi.curios.effects;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.ItemStack;
import org.taichi.curios.TaiCurioEffectContext;
import org.taichi.curios.TaiCurioEffectType;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class DamageImmuneEffectContext extends TaiCurioEffectContext {
    private List<TagKey<DamageType>> immuneDamageTags;
    private List<ResourceKey<DamageType>> immuneDamageTypes;

    public DamageImmuneEffectContext(TaiCurioEffectType<?> type, ItemStack stack, SlotContext curioContext) {
        super(type, stack, curioContext);

    }

    public void setImmuneDamageTags(List<TagKey<DamageType>> immuneDamageTags) {
        this.immuneDamageTags = immuneDamageTags;
    }
    public void setImmuneDamageTypes(List<ResourceKey<DamageType>> immuneDamageTypes) {
        this.immuneDamageTypes = immuneDamageTypes;
    }

    public boolean isImmuneTo(DamageSource damageSource) {
        if (immuneDamageTypes != null) {
            for (ResourceKey<DamageType> key : immuneDamageTypes) {
                if(damageSource.is(key)) {
                    return true;
                }
            }
        }

        if (immuneDamageTags != null) {
            for (TagKey<DamageType> tag : immuneDamageTags) {
                if(damageSource.is(tag)) {
                    return true;
                }
            }
        }

        return false;
    }

}
