package org.taichi.network.c2s;

import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.handling.IPayloadContext;
import org.taichi.TaiChiMod;

public record PlayerDoubleJumpPacket(Vec3 pos) implements CustomPacketPayload {
    public static final CustomPacketPayload.Type<PlayerDoubleJumpPacket> TYPE = new CustomPacketPayload.Type<>(TaiChiMod.loc("double_jump_property_sync"));

    public static final StreamCodec<RegistryFriendlyByteBuf, PlayerDoubleJumpPacket> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.fromCodec(Vec3.CODEC),
            PlayerDoubleJumpPacket::pos,
            PlayerDoubleJumpPacket::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }

    public void handle(IPayloadContext ctx) {
        ServerPlayer player = (ServerPlayer) ctx.player();
        if (!player.onGround()) {
            player.startingToFallPosition = this.pos;
            player.fallDistance = (float) Math.max(0.0, this.pos.y - ctx.player().getY());

            Level level = player.level();

            // Play effects
            level.addParticle(ParticleTypes.CLOUD, player.getX(), player.getY() + 0.5, player.getZ(), 1.0, 1.0, 1.0);
            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 0.1F, 1.0F + (float) level.random.nextGaussian() * 0.05F);

        }
    }
}
