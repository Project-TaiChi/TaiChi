package org.taichi.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.taichi.TaiChiMod;
import org.taichi.capability.TaiCurioEffectHandler;
import top.theillusivec4.curios.api.CuriosCapability;
import top.theillusivec4.curios.api.type.capability.ICurio;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


public class TaiCapabilities {
    public static EntityCapability<TaiCurioEffectHandler, Void> TAI_CURIO_EFFECT_HANDLER = EntityCapability.createVoid(TaiChiMod.loc("curio_effect"), TaiCurioEffectHandler.class);

    public static Map<ResourceLocation, Function<ItemStack, ICurio>> curioFactories = new HashMap<>();


    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.registerEntity(TAI_CURIO_EFFECT_HANDLER, EntityType.PLAYER, TaiCurioEffectHandler::provider);

        for (Map.Entry<ResourceLocation, Function<ItemStack, ICurio>> entry : curioFactories.entrySet()) {
            Item item = BuiltInRegistries.ITEM.get(entry.getKey());
            Function<ItemStack, ICurio> factory = entry.getValue();
            event.registerItem(CuriosCapability.ITEM, (stack, context) -> factory.apply(stack), item);
        }
    }

    public static void init(IEventBus modbus) {
        modbus.addListener(TaiCapabilities::registerCapabilities);
    }
}
