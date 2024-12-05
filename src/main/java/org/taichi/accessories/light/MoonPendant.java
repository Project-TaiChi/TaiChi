package org.taichi.accessories.light;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.AABB;
import top.theillusivec4.curios.api.SlotContext;

import java.util.List;

public class MoonPendant extends BasicLightAccessory {
    public MoonPendant() {
        super(1);
    }

    @Override
    protected void tickInternal(ItemStack stack, SlotContext context, Player player) {
        var level = player.level();
        if (level.isNight()) {
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 500));
            AABB aabb = new AABB(player.getOnPos()).inflate(32);
            List<Monster> list = level.getEntitiesOfClass(Monster.class, aabb);
            for (Monster monster : list) {
                monster.addEffect(new MobEffectInstance(MobEffects.GLOWING, 100));
            }
        } else {
            //TODO:day time effect
        }

    }
}
