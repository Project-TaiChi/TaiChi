package org.taichi.network.s2c;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.taichi.TaiChiMod;
import org.taichi.client.AttackReactionBehaviorHandler;

import java.util.List;
import java.util.UUID;

public record AttackReactionSync(
        boolean enabled,
        List<UUID> glowingEntities
)  implements CustomPacketPayload{
    public static final CustomPacketPayload.Type<AttackReactionSync> TYPE = new CustomPacketPayload.Type<>(TaiChiMod.loc("moon_pendant_behavior_sync"));

    // Each pair of elements defines the stream codec of the element to encode/decode and the getter for the element to encode
    // 'name' will be encoded and decoded as a string
    // 'age' will be encoded and decoded as an integer
    // The final parameter takes in the previous parameters in the order they are provided to construct the payload object
    public static final StreamCodec<ByteBuf, AttackReactionSync> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.BOOL,
            AttackReactionSync::enabled,
            UUIDUtil.STREAM_CODEC.apply(ByteBufCodecs.list()),
            AttackReactionSync::glowingEntities,
            AttackReactionSync::new
    );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext ctx) {
        AttackReactionBehaviorHandler.setEnabled(enabled());
        AttackReactionBehaviorHandler.updateGlowingEntities(glowingEntities());

    }
}
