package org.taichi.curios.type;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.Targeting;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.taichi.capability.TaiCurioEffectHandler;
import org.taichi.curios.TaiCurioEffectType;
import org.taichi.curios.effects.AttackerReactionContext;
import org.taichi.init.TaiCapabilities;
import org.taichi.init.TaiCurioEffects;
import org.taichi.network.s2c.AttackReactionSync;
import top.theillusivec4.curios.api.SlotContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AttackerReaction extends TaiCurioEffectType<AttackerReactionContext> {

    public AttackerReaction() {
        super(false,true);
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, this::onLivingChangeTarget);
    }


    @Override
    public AttackerReactionContext create(ItemStack stack, SlotContext curioContext) {
        return new AttackerReactionContext(stack, curioContext);
    }


    @Override
    public void effectAdded(LivingEntity entity, ItemStack stack, AttackerReactionContext context) {
        if (!(entity instanceof ServerPlayer serverPlayer)) {
            return;
        }
        PacketDistributor.sendToPlayer(serverPlayer, createSyncPacket(context));
    }

    @Override
    public void effectRemoved(LivingEntity entity, ItemStack stack, AttackerReactionContext context) {
        if (!(entity instanceof ServerPlayer serverPlayer)) {
            return;
        }
        PacketDistributor.sendToPlayer(serverPlayer, new AttackReactionSync(false, new ArrayList<>()));
    }

    public static boolean isHostile(LivingEntity entity) {
        return entity instanceof Enemy || entity instanceof NeutralMob;
    }


    private AttackerReactionContext getContext(ServerPlayer player) {
        TaiCurioEffectHandler handler = player.getCapability(TaiCapabilities.TAI_CURIO_EFFECT_HANDLER);
        if (handler == null) {
            return null;
        }

        return handler.findFirstEffect(TaiCurioEffects.ATTACKER_REACTION.get());
    }

    private void addAttacker(ServerPlayer player, LivingEntity entity) {
        AttackerReactionContext ctx = getContext(player);
        if (ctx != null && !ctx.getNearbyAttackers().contains(entity)) {
            ctx.getNearbyAttackers().add(entity);
            PacketDistributor.sendToPlayer(player, createSyncPacket(ctx));
        }
    }

    private void removeAttacker(ServerPlayer player, LivingEntity entity) {
        AttackerReactionContext ctx = getContext(player);
        if (ctx != null && ctx.getNearbyAttackers().contains(entity)) {
            ctx.getNearbyAttackers().remove(entity);
            PacketDistributor.sendToPlayer(player, createSyncPacket(ctx));
        }
    }


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

    private AttackReactionSync createSyncPacket(AttackerReactionContext context) {
        if (context == null) {
            return new AttackReactionSync(false, new ArrayList<>());
        }
        List<UUID> attackers = new ArrayList<>();
        for (LivingEntity entity : context.getNearbyAttackers()) {
            attackers.add(entity.getUUID());
        }
        return new AttackReactionSync(true, attackers);
    }


}
