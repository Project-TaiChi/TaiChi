package org.taichi.event;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffects;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.ClientHooks;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.CommonHooks;
import net.neoforged.neoforge.network.PacketDistributor;
import org.taichi.network.c2s.DoubleJumpPropertySync;

import java.util.HashMap;
import java.util.Map;

@EventBusSubscriber(modid = "taichi", value = Dist.CLIENT)
public class DoubleJumpHandler {

    private static boolean doubleJump = false;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Pre event) {
        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player = mc.player;
        if (player == null) {
            return;
        }

        boolean spacePressed = mc.options.keyJump.isDown();

        if (spacePressed && !doubleJump) {
            doubleJump = true;
        }

        if (spacePressed && !player.onGround() && !player.isInWater() && player.getJumpRidingScale() == 0 && doubleJump) {
            // Play effects
            mc.level.addParticle(ParticleTypes.CLOUD, player.getX(), player.getY() + 0.5, player.getZ(), 1.0, 1.0, 1.0);
            mc.level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.PLAYER_ATTACK_SWEEP, SoundSource.PLAYERS, 0.1F, 1.0F + (float) mc.level.random.nextGaussian() * 0.05F);

            // Apply double jump
            doubleJump = false;
            player.setDeltaMovement(player.getDeltaMovement().x, 0.75, player.getDeltaMovement().z);

            if (player.hasEffect(MobEffects.JUMP)) {
                player.setDeltaMovement(player.getDeltaMovement().x, player.getDeltaMovement().y + (double) ((float) (player.getEffect(MobEffects.JUMP).getAmplifier() + 1) * 0.1F), player.getDeltaMovement().z);
            }

            if (player.isSprinting()) {
                float f = player.getYRot() * 0.017453292F;
                player.setDeltaMovement(player.getDeltaMovement().x - (double) (Mth.sin(f) * 0.2F), player.getDeltaMovement().y, player.getDeltaMovement().z + (double) (Mth.cos(f) * 0.2F));
            }

            player.fallDistance = 0.0F;
            PacketDistributor.sendToServer(new DoubleJumpPropertySync(player.getY()));
            CommonHooks.onLivingJump(player);
        }

        if (player.onGround() && player.getJumpRidingScale() == 0) {
            doubleJump = false;
        }
    }
}