package org.taichi.network.s2c;

import com.mojang.logging.LogUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.slf4j.Logger;
import org.taichi.TaiChiMod;
import org.taichi.client.AttackReactionBehaviorHandler;
import org.taichi.client.SimpleEffectHandler;
import org.taichi.curios.TaiCurioEffectType;
import org.taichi.curios.type.SimpleEffect;
import org.taichi.init.TaiCurioEffects;

public record SimpleEffectSync(
        boolean add,
        TaiCurioEffectType<?> effectType
) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<SimpleEffectSync> TYPE = new CustomPacketPayload.Type<>(TaiChiMod.loc("simple_effect_sync"));
    private static final Logger logger = LogUtils.getLogger();

    public static final StreamCodec<RegistryFriendlyByteBuf, SimpleEffectSync> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            SimpleEffectSync::add,
            ByteBufCodecs.registry(TaiCurioEffects.KEY_CURIOS_EFFECT),
            SimpleEffectSync::effectType,
            SimpleEffectSync::new
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext ctx) {
        if(!(this.effectType instanceof SimpleEffect simpleEffect)) {
            logger.error("SimpleEffectSync received with non-SimpleEffect type: {}", this.effectType);
            return;
        }
        SimpleEffectHandler.handle(simpleEffect, this.add);

    }
}
