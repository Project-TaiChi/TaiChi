package org.taichi.accessories.light;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.Targeting;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.taichi.network.s2c.MoonPendantBehaviorSync;
import top.theillusivec4.curios.api.SlotContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class MoonPendant extends BasicLightAccessory {
    public MoonPendant() {
        super(1);
        NeoForge.EVENT_BUS.addListener(this::onLivingChangeTarget);
    }

    private record Context(Player player, List<LivingEntity> attackers) {
    }

    private final HashMap<Player, Context> equippedPlayers = new HashMap<>();

    private MoonPendantBehaviorSync createSyncPacket(Player player) {
        Context ctx = equippedPlayers.get(player);
        if (ctx == null) {
            return new MoonPendantBehaviorSync(false, new ArrayList<>());
        }
        List<UUID> attackers = new ArrayList<>();
        for (LivingEntity entity : ctx.attackers) {
            attackers.add(entity.getUUID());
        }
        return new MoonPendantBehaviorSync(true, attackers);
    }


    @Override
    public void onEquip(SlotContext context, ItemStack prevStack, ItemStack stack) {
        if (!(context.entity() instanceof ServerPlayer player)) {
            return;
        }
        equippedPlayers.put(player, new Context(player, new ArrayList<>()));
        PacketDistributor.sendToPlayer(player, createSyncPacket(player));


    }

    @Override
    public void onUnequip(SlotContext context, ItemStack newStack, ItemStack stack) {
        if (!(context.entity() instanceof ServerPlayer player)) {
            return;
        }
        equippedPlayers.remove(player);
        PacketDistributor.sendToPlayer(player, new MoonPendantBehaviorSync(false, new ArrayList<>()));
    }

    private boolean isHostile(LivingEntity entity) {
        return entity instanceof Enemy || entity instanceof NeutralMob;
    }

    private void addAttacker(ServerPlayer player, LivingEntity entity) {
        Context ctx = equippedPlayers.get(player);
        if (ctx != null && !ctx.attackers.contains(entity)) {
            ctx.attackers.add(entity);
            PacketDistributor.sendToPlayer(player, createSyncPacket(player));
        }
    }

    private void removeAttacker(ServerPlayer player, LivingEntity entity) {
        Context ctx = equippedPlayers.get(player);
        if (ctx != null && ctx.attackers.contains(entity)) {
            ctx.attackers.remove(entity);
            PacketDistributor.sendToPlayer(player, createSyncPacket(player));
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    private void onLivingChangeTarget(LivingChangeTargetEvent event) {
        if (!(event.getEntity() instanceof Targeting entity) || !isHostile(event.getEntity())) {
            return;
        }
        LivingEntity previousTarget = entity.getTarget();
        if (previousTarget == event.getNewAboutToBeSetTarget()) {
            // No change
            if (event.getNewAboutToBeSetTarget() instanceof ServerPlayer player) {
                addAttacker(player, event.getEntity());
            }
            return;
        }

        if (previousTarget instanceof ServerPlayer player) {
            removeAttacker(player, event.getEntity());
        }

        if (event.getNewAboutToBeSetTarget() instanceof ServerPlayer player) {
            addAttacker(player, event.getEntity());
        }
    }

}
