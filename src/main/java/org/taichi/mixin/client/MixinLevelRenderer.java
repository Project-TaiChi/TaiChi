package org.taichi.mixin.client;

import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.taichi.client.MoonPendantBehaviorHandler;

@Mixin(LevelRenderer.class)
public abstract class MixinLevelRenderer {

    @Redirect(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Entity;getTeamColor()I"))
    private int redirect_getTeamColor(Entity entity) {
        if(!(entity instanceof LivingEntity livingEntity)) {
            return entity.getTeamColor();
        }

        if(!MoonPendantBehaviorHandler.isGlowing(livingEntity)) {
            return entity.getTeamColor();
        }

        return 0xFF3344;
    }
}
