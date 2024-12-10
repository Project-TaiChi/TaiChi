package org.taichi.curios.effects;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.Targeting;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.monster.Enemy;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.living.LivingChangeTargetEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.taichi.capability.TaiCurioEffectHandler;
import org.taichi.curios.IContextCurioEffect;
import org.taichi.init.TaiCapabilities;
import org.taichi.init.TaiCurioEffects;
import org.taichi.network.s2c.AttackReactionSync;
import org.taichi.utils.EquippedCurio;
import top.theillusivec4.curios.api.SlotContext;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AttackerReaction implements IContextCurioEffect<AttackerReaction.Context> {

    public AttackerReaction() {
        NeoForge.EVENT_BUS.addListener(EventPriority.LOWEST, this::onLivingChangeTarget);
    }


    @Override
    public boolean sync() {
        return false;
    }

    @Override
    public boolean persist() {
        return false;
    }

    @Override
    public boolean syncSelf() {
        return true;
    }

    @Override
    public Context createContext(SlotContext slotContext, ItemStack stack) {
        if (!(slotContext.entity() instanceof ServerPlayer serverPlayer)) {
            return null;
        }
        List<LivingEntity> nearbyAttackers = findAttackersNearby(serverPlayer);
        return new Context(nearbyAttackers);
    }


    public record Context(List<LivingEntity> attackers) {
    }

    private boolean isAttacker(LivingEntity livingEntity, ServerPlayer player) {
        if (!isHostile(livingEntity)) {
            return false;
        }

        if (livingEntity instanceof Mob mob && mob.getTarget() != null) {
            return mob.getTarget() == player;
        }

        if (livingEntity.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET)) {
            return livingEntity.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).filter(target -> target == player).isPresent();
        }

        return false;
    }

    private List<LivingEntity> findAttackersNearby(ServerPlayer player) {
        return player.level().getEntities(EntityTypeTest.forClass(LivingEntity.class), player.getBoundingBox().inflate(24), entity -> isAttacker(entity, player));
    }

    @Override
    public void effectAdded(LivingEntity entity, ItemStack stack) {
        if (!(entity instanceof ServerPlayer serverPlayer)) {
            return;
        }
        PacketDistributor.sendToPlayer(serverPlayer, createSyncPacket(serverPlayer));
    }

    @Override
    public void effectRemoved(LivingEntity entity, ItemStack stack) {
        if (!(entity instanceof ServerPlayer serverPlayer)) {
            return;
        }
        PacketDistributor.sendToPlayer(serverPlayer, new AttackReactionSync(false, new ArrayList<>()));
    }

    private boolean isHostile(LivingEntity entity) {
        return entity instanceof Enemy || entity instanceof NeutralMob;
    }

    private Context getContext(ServerPlayer player) {
        TaiCurioEffectHandler handler = player.getCapability(TaiCapabilities.TAI_CURIO_EFFECT_HANDLER);
        if (handler == null) {
            return null;
        }

        EquippedCurio effectProvider = handler.findEffectProvider(TaiCurioEffects.ATTACKER_REACTION.get());
        if (effectProvider == null) {
            return null;
        }

        return handler.getEffectContext(TaiCurioEffects.ATTACKER_REACTION.get());

    }

    private void addAttacker(ServerPlayer player, LivingEntity entity) {
        Context ctx = getContext(player);
        if (ctx != null && !ctx.attackers.contains(entity)) {
            ctx.attackers.add(entity);
            PacketDistributor.sendToPlayer(player, createSyncPacket(player));
        }
    }

    private void removeAttacker(ServerPlayer player, LivingEntity entity) {
        Context ctx = getContext(player);
        if (ctx != null && ctx.attackers.contains(entity)) {
            ctx.attackers.remove(entity);
            PacketDistributor.sendToPlayer(player, createSyncPacket(player));
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

    private AttackReactionSync createSyncPacket(ServerPlayer player) {
        Context ctx = getContext(player);
        if (ctx == null) {
            return new AttackReactionSync(false, new ArrayList<>());
        }
        List<UUID> attackers = new ArrayList<>();
        for (LivingEntity entity : ctx.attackers) {
            attackers.add(entity.getUUID());
        }
        return new AttackReactionSync(true, attackers);
    }


}
