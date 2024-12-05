package org.taichi.mixin.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.taichi.client.MoonPendantBehaviorHandler;

@Mixin(Minecraft.class)
public abstract class MixinMinecraft {

    @Inject(method = "shouldEntityAppearGlowing", at = @At("HEAD"), cancellable = true)
    private void inject_isCurrentlyGlowing(Entity entity, CallbackInfoReturnable<Boolean> cir) {
        if(!(entity instanceof LivingEntity livingEntity)) {
            return;
        }

        if(!MoonPendantBehaviorHandler.isGlowing(livingEntity)) {
            return;
        }

        cir.setReturnValue(true);
        cir.cancel();
    }
}
