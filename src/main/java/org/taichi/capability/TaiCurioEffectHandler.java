package org.taichi.capability;

import net.minecraft.world.entity.player.Player;
import org.taichi.curios.TaiCurioEffectContext;
import org.taichi.curios.TaiCurioEffectType;
import org.taichi.init.TaiAttachments;

import java.util.Collections;
import java.util.List;

public class TaiCurioEffectHandler {

    private final Player player;

    public TaiCurioEffectHandler(Player player) {
        this.player = player;
    }

    public static TaiCurioEffectHandler provider(Player player, Void unused) {
        return new TaiCurioEffectHandler(player);
    }


    public boolean hasEffect(TaiCurioEffectType<?> effect) {
        if (player.level().isClientSide) {
            // TODO: Implement client side logic
            return false;
        }
        TaiCurioEffectContext firstEffect = player.getData(TaiAttachments.ENTITY_EFFECT).findFirstEffect(effect);
        return firstEffect != null;
    }

    public <T extends TaiCurioEffectContext> T findFirstEffect(TaiCurioEffectType<T> effect) {
        if (player.level().isClientSide) {
            // TODO: Implement client side logic
            return null;
        }
        return player.getData(TaiAttachments.ENTITY_EFFECT).findFirstEffect(effect);
    }

    public <T extends TaiCurioEffectContext> List<T> findEffects(TaiCurioEffectType<T> effect) {
        if (player.level().isClientSide) {
            // TODO: Implement client side logic
            return Collections.emptyList();
        }
        return player.getData(TaiAttachments.ENTITY_EFFECT).findEffects(effect);
    }

}
