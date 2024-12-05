package org.taichi.command;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.taichi.TaiChiMod;
import org.taichi.accessories.AccessoryType;
import org.taichi.init.TaiAttachment;

import static net.minecraft.commands.Commands.literal;

@EventBusSubscriber(modid = TaiChiMod.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class TaiCommands {


    @SubscribeEvent
    private static void registerCommand(RegisterCommandsEvent event) {
        event.getDispatcher().register(
                LiteralArgumentBuilder.<CommandSourceStack>literal("taichi")
                        .then(DebugCommand.register())
        );
    }

    private static class DebugCommand {
        private static ArgumentBuilder<CommandSourceStack, ?> register() {
            return literal("set")
                    .requires(cs -> cs.hasPermission(2))
                    .then(literal(AccessoryType.DARK.name()).executes(ctx -> {
                        Entity entity = ctx.getSource().getEntity();
                        if (entity instanceof Player player) {
                            player.setData(TaiAttachment.PLAYER_ACCESSORY, AccessoryType.DARK);
                            ctx.getSource().sendSuccess(() -> Component.literal("Set player to dark accessory"), true);
                        } else ctx.getSource().sendFailure(Component.literal("You must be a player to use this command"));
                        return 0;
                    })).then(literal(AccessoryType.LIGHT.name()).executes(ctx -> {
                        Entity entity = ctx.getSource().getEntity();
                        if (entity instanceof Player player) {
                            player.setData(TaiAttachment.PLAYER_ACCESSORY, AccessoryType.LIGHT);
                            ctx.getSource().sendSuccess(() -> Component.literal("Set player to light accessory"), true);
                        } else ctx.getSource().sendFailure(Component.literal("You must be a player to use this command"));
                        return 0;
                    }));
        }
    }

}
