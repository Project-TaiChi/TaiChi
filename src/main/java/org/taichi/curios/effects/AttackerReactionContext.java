package org.taichi.curios.effects;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.entity.EntityTypeTest;
import org.taichi.curios.TaiCurioEffectContext;
import org.taichi.init.TaiCurioEffects;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

import static org.taichi.curios.type.AttackerReaction.isHostile;

public class AttackerReactionContext extends TaiCurioEffectContext {
    private final List<LivingEntity> nearbyAttackers;

    public AttackerReactionContext(ItemStack stack, SlotContext curioContext) {
        super(TaiCurioEffects.ATTACKER_REACTION.get(), stack, curioContext);
        this.nearbyAttackers = findAttackersNearby(curioContext.entity());
    }

    private List<LivingEntity> findAttackersNearby(LivingEntity livingEntity) {
        if(!(livingEntity instanceof ServerPlayer player)) {
            return List.of();
        }
        return player.level().getEntities(EntityTypeTest.forClass(LivingEntity.class), player.getBoundingBox().inflate(24), entity -> isAttacker(entity, player));
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

    public List<LivingEntity> getNearbyAttackers() {
        return nearbyAttackers;
    }


}
