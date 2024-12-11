package org.taichi.utils.builder;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.taichi.TaiChiMod;
import top.theillusivec4.curios.api.CurioAttributeModifiers;

public class TaiCurioAttributeModifiersBuilder {
    private final String namePrefix;
    private final CurioAttributeModifiers.Builder builder;
    private final String slot;


    public TaiCurioAttributeModifiersBuilder(String namePrefix, CurioAttributeModifiers.Builder builder, String slot) {
        this.namePrefix = namePrefix;
        this.builder = builder;
        this.slot = slot;
    }

    private void add(Holder<Attribute> attributeHolder, String name, double value, AttributeModifier.Operation operation) {
        builder.add(attributeHolder, new AttributeModifier(TaiChiMod.loc(namePrefix + "." + name), value, operation), slot);
    }


    public TaiCurioAttributeModifiersBuilder add(Holder<Attribute> attributeHolder, String name, double value) {
        add(attributeHolder, name, value, AttributeModifier.Operation.ADD_VALUE);
        return this;
    }

    public TaiCurioAttributeModifiersBuilder multiply(Holder<Attribute> attributeHolder, String name, double value) {
        add(attributeHolder, name, value, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        return this;
    }

    public TaiCurioAttributeModifiersBuilder multiplyTotal(Holder<Attribute> attributeHolder, String name, double value) {
        add(attributeHolder, name, value, AttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);
        return this;
    }

    public CurioAttributeModifiers build() {
        return builder.build();
    }

}
