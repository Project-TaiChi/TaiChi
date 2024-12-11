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
    public static final CustomPacketPayload.Type<AttackReactionSync> TYPE = new CustomPacketPayload.Type<>(TaiChiMod.loc("attack_reaction_sync"));

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
