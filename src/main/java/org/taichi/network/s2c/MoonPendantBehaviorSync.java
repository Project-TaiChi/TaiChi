package org.taichi.network.s2c;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.taichi.TaiChiMod;
import org.taichi.client.MoonPendantBehaviorHandler;

import java.util.List;
import java.util.UUID;

public record MoonPendantBehaviorSync (
        boolean enabled,
        List<UUID> glowingEntities
)  implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<MoonPendantBehaviorSync> TYPE = new CustomPacketPayload.Type<>(TaiChiMod.loc("moon_pendant_behavior_sync"));

    // Each pair of elements defines the stream codec of the element to encode/decode and the getter for the element to encode
    // 'name' will be encoded and decoded as a string
    // 'age' will be encoded and decoded as an integer
    // The final parameter takes in the previous parameters in the order they are provided to construct the payload object
    public static final StreamCodec<ByteBuf, MoonPendantBehaviorSync> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            MoonPendantBehaviorSync::enabled,
            UUIDUtil.STREAM_CODEC.apply(ByteBufCodecs.list()),
            MoonPendantBehaviorSync::glowingEntities,
            MoonPendantBehaviorSync::new
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext ctx) {
        MoonPendantBehaviorHandler.setEnabled(enabled());
        MoonPendantBehaviorHandler.updateGlowingEntities(glowingEntities());

    }
}
