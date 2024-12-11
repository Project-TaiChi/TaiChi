package org.taichi.curios.type;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.network.PacketDistributor;
import org.taichi.curios.TaiCurioEffectContext;
import org.taichi.curios.TaiCurioEffectType;
import org.taichi.network.s2c.SimpleEffectSync;
import top.theillusivec4.curios.api.SlotContext;

public class SimpleEffect extends TaiCurioEffectType<TaiCurioEffectContext> {
    private final boolean syncClient;

    public SimpleEffect(boolean persist, boolean unique, boolean syncClient) {
        super(persist, unique);
        this.syncClient = syncClient;
    }

    @Override
    public TaiCurioEffectContext create(ItemStack stack, SlotContext curioContext) {
        return new TaiCurioEffectContext(this, stack, curioContext);
    }

    @Override
    public void effectAdded(LivingEntity entity, ItemStack stack, TaiCurioEffectContext context) {
        if (syncClient && (entity instanceof ServerPlayer player)) {
            PacketDistributor.sendToPlayer(player, new SimpleEffectSync(true, context.getType()));
        }
    }

    @Override
    public void effectRemoved(LivingEntity entity, ItemStack stack, TaiCurioEffectContext context) {
        if (syncClient && (entity instanceof ServerPlayer player)) {
            PacketDistributor.sendToPlayer(player, new SimpleEffectSync(false, context.getType()));
        }
    }
}
