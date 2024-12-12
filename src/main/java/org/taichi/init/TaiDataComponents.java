package org.taichi.init;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.taichi.TaiChiMod;
import org.taichi.component.TaiCurioAttributeModifiers;

import java.util.function.Supplier;

public class TaiDataComponents {

    public static final DeferredRegister.DataComponents DATA_COMPONENTS = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, TaiChiMod.MOD_ID);

    public static final Supplier<DataComponentType<TaiCurioAttributeModifiers>>
            TAI_CURIO_ATTRIBUTE_MODIFIERS = DATA_COMPONENTS.register("attribute_modifiers",
            () -> DataComponentType.<TaiCurioAttributeModifiers>builder()
                    .persistent(TaiCurioAttributeModifiers.CODEC)
                    .networkSynchronized(TaiCurioAttributeModifiers.STREAM_CODEC)
                    .cacheEncoding()
                    .build());

    public static void init(IEventBus modbus) {
        DATA_COMPONENTS.register(modbus);
    }
}
