package org.taichi.utils.builder;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import org.taichi.TaiChiMod;
import org.taichi.component.TaiCurioAttributeModifiers;
import top.theillusivec4.curios.CuriosConstants;
import top.theillusivec4.curios.api.CurioAttributeModifiers;
import top.theillusivec4.curios.api.SlotAttribute;

import java.util.ArrayList;
import java.util.List;

public class TaiCurioAttributeModifiersBuilder {
    private final String namePrefix;
    private List<TaiCurioAttributeModifiers.Entry> entries;


    public TaiCurioAttributeModifiersBuilder(String namePrefix) {
        this.namePrefix = namePrefix;
        this.entries = new ArrayList<>();
    }

    private void add(Holder<Attribute> attributeHolder, String name, double value, AttributeModifier.Operation operation) {
//        builder.add(attributeHolder, new AttributeModifier(TaiChiMod.loc(namePrefix + "." + name), value, operation));
        AttributeModifier attributeModifier = new AttributeModifier(TaiChiMod.loc(namePrefix + "." + name), value, operation);

        ResourceLocation rl;

        if (attributeHolder.value() instanceof SlotAttribute wrapper) {
            rl = ResourceLocation.fromNamespaceAndPath(CuriosConstants.MOD_ID, wrapper.getIdentifier());
        } else {
            rl = ResourceLocation.parse(attributeHolder.getRegisteredName());
        }
        this.entries.add(new TaiCurioAttributeModifiers.Entry(rl, attributeModifier));
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

    public TaiCurioAttributeModifiers build() {
        return new TaiCurioAttributeModifiers(entries);
    }
}
