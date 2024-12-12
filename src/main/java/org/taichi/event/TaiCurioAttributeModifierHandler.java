package org.taichi.event;

import com.google.common.collect.Multimap;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import org.taichi.component.TaiCurioAttributeModifiers;
import org.taichi.init.TaiDataComponents;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotAttribute;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.event.CurioAttributeModifierEvent;

@EventBusSubscriber
public class TaiCurioAttributeModifierHandler {
    @SubscribeEvent
    public static void onCurioAttributeModifierEvent(CurioAttributeModifierEvent event) {
        TaiCurioAttributeModifiers attributemodifiers =
                event.getItemStack().getOrDefault(TaiDataComponents.TAI_CURIO_ATTRIBUTE_MODIFIERS, TaiCurioAttributeModifiers.EMPTY);

        SlotContext slotContext = event.getSlotContext();
        ResourceLocation id = event.getId();
        for (TaiCurioAttributeModifiers.Entry modifier : attributemodifiers.modifiers()) {

            ResourceLocation rl = modifier.attribute();
            AttributeModifier attributeModifier = modifier.modifier();

            if (rl != null) {
                AttributeModifier.Operation operation = attributeModifier.operation();
                double amount = attributeModifier.amount();

                if (rl.getNamespace().equals("curios")) {
                    String identifier1 = rl.getPath();
                    LivingEntity livingEntity = slotContext.entity();
                    boolean clientSide = livingEntity == null || livingEntity.level().isClientSide();

                    if (CuriosApi.getSlot(identifier1, clientSide).isPresent()) {
                        event.addModifier(SlotAttribute.getOrCreate(identifier1), new AttributeModifier(id, amount, operation));

                    }
                } else {
                    Holder<Attribute> attribute =
                            BuiltInRegistries.ATTRIBUTE.getHolder(rl).orElse(null);

                    if (attribute != null) {
                        event.addModifier(attribute, new AttributeModifier(id, amount, operation));
                    }
                }

            }
        }
    }
}
