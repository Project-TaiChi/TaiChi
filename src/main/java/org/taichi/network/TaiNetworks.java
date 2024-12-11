package org.taichi.network;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.HandlerThread;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import org.taichi.TaiChiMod;
import org.taichi.network.s2c.AttackReactionSync;
import org.taichi.network.s2c.SimpleEffectSync;

@EventBusSubscriber(modid = TaiChiMod.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class TaiNetworks {
    @SubscribeEvent
    public static void register(final RegisterPayloadHandlersEvent event) {
        // Sets the current network version
        final PayloadRegistrar registrar = event.registrar("1")
                .executesOn(HandlerThread.MAIN);

        registrar.playToClient(
                AttackReactionSync.TYPE,
                AttackReactionSync.STREAM_CODEC,
                AttackReactionSync::handle
        );

        registrar.playToClient(
                SimpleEffectSync.TYPE,
                SimpleEffectSync.STREAM_CODEC,
                SimpleEffectSync::handle
        );
    }
}
