package org.taichi.event;

import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.PlayLevelSoundEvent;
import org.taichi.client.SimpleEffectHandler;
import org.taichi.init.TaiCurioEffects;
import org.taichi.utils.CapabilityHelper;

@EventBusSubscriber
public class MuteSoundHandler {

    @SubscribeEvent
    public static void onPlaySound(PlayLevelSoundEvent.AtEntity event) {
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        boolean mute = false;
        if (player.level().isClientSide) {
            mute = SimpleEffectHandler.hasEffect(TaiCurioEffects.MUTE_SOUND.get());
        } else {
            mute = CapabilityHelper.findEntityEffectContext(player, TaiCurioEffects.MUTE_SOUND.get()) != null;
        }

        if (mute) {
            event.setCanceled(true);
        }
    }
}
