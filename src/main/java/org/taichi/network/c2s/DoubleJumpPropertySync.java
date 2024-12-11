package org.taichi.network.c2s;

import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.taichi.TaiChiMod;

public record DoubleJumpPropertySync(double y) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<DoubleJumpPropertySync> TYPE = new CustomPacketPayload.Type<>(TaiChiMod.loc("double_jump_property_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, DoubleJumpPropertySync> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.DOUBLE,
            DoubleJumpPropertySync::y,
            DoubleJumpPropertySync::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext ctx) {
        if (!ctx.player().onGround()) {
            ctx.player().fallDistance = (float) Math.max(0.0, this.y - ctx.player().getY());
        }
    }
}
