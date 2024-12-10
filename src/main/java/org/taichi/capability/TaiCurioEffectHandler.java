package org.taichi.capability;

import net.minecraft.Util;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import org.taichi.curios.IContextCurioEffect;
import org.taichi.curios.ICuriosEffect;
import org.taichi.init.TaiAttachments;
import org.taichi.init.TaiCurioEffects;
import org.taichi.utils.EquippedCurio;

import java.util.Set;

public class TaiCurioEffectHandler {

    private final Player player;

    public TaiCurioEffectHandler(Player player) {
        this.player = player;
    }

    public static TaiCurioEffectHandler provider(Player player, Void unused) {
        return new TaiCurioEffectHandler(player);
    }


    public boolean hasEffect(ICuriosEffect effect) {
        if (player.level().isClientSide) {
            // TODO: Implement client side logic
            return false;
        }
        String key = Util.getRegisteredName(TaiCurioEffects.REGISTRY_CURIOS_EFFECT, effect);
        Set<EquippedCurio> curios = player.getData(TaiAttachments.ENTITY_EFFECT).findCurios(key);
        return !curios.isEmpty();
    }

    public EquippedCurio findEffectProvider(ICuriosEffect effect) {
        if (player.level().isClientSide) {
            // TODO: Implement client side logic
            return null;
        }

        String key = Util.getRegisteredName(TaiCurioEffects.REGISTRY_CURIOS_EFFECT, effect);
        Set<EquippedCurio> curios = player.getData(TaiAttachments.ENTITY_EFFECT).findCurios(key);
        return curios.stream().findAny().orElse(null);
    }

    @SuppressWarnings("unchecked")
    public <T> T getEffectContext(IContextCurioEffect<T> effect) {
        if (player.level().isClientSide) {
            return null;
        }
        String key = Util.getRegisteredName(TaiCurioEffects.REGISTRY_CURIOS_EFFECT, effect);
        return (T) player.getData(TaiAttachments.ENTITY_EFFECT).getEffectContext(key);
    }

}
