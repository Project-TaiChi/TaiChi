package org.taichi.curios;

import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import top.theillusivec4.curios.api.SlotContext;

import java.util.stream.Stream;

public abstract class TaiCurioEffectType<T extends TaiCurioEffectContext> {
    private final boolean persist;
    private final boolean unique;


    public enum CodecType {
        Sync,
        Persist,
        SyncSelf,
    }

    public TaiCurioEffectType(boolean persist, boolean unique) {
        this.persist = persist;
        this.unique = unique;
    }

    public abstract T create(ItemStack stack, SlotContext curioContext);

    public boolean persist() {
        return persist;
    }


    public boolean unique() {
        return unique;
    }


    public void effectAdded(LivingEntity entity, ItemStack stack, T context) {
    }

    public void effectRemoved(LivingEntity entity, ItemStack stack, T context) {
    }


    @FunctionalInterface
    public interface CurioEffectSupplier<T extends TaiCurioEffectContext> {
        T create(ItemStack stack, SlotContext curioContext);
    }
}
