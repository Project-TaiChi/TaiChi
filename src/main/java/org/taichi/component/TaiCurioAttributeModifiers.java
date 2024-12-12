package org.taichi.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;

import java.util.List;

public record TaiCurioAttributeModifiers(List<Entry> modifiers) {
    public static final TaiCurioAttributeModifiers EMPTY = new TaiCurioAttributeModifiers(List.of());
    private static final Codec<TaiCurioAttributeModifiers> FULL_CODEC = RecordCodecBuilder.create(
            p_337947_ -> p_337947_.group(TaiCurioAttributeModifiers.Entry.CODEC.listOf().fieldOf("modifiers").forGetter(TaiCurioAttributeModifiers::modifiers))
                    .apply(p_337947_, TaiCurioAttributeModifiers::new)
    );
    public static final Codec<TaiCurioAttributeModifiers> CODEC = Codec.withAlternative(
            FULL_CODEC, TaiCurioAttributeModifiers.Entry.CODEC.listOf(), TaiCurioAttributeModifiers::new
    );
    public static final StreamCodec<RegistryFriendlyByteBuf, TaiCurioAttributeModifiers>
            STREAM_CODEC = StreamCodec.composite(
            TaiCurioAttributeModifiers.Entry.STREAM_CODEC.apply(ByteBufCodecs.list()),
            TaiCurioAttributeModifiers::modifiers,
            TaiCurioAttributeModifiers::new
    );

    public record Entry(ResourceLocation attribute, AttributeModifier modifier) {
        public static final Codec<Entry> CODEC = RecordCodecBuilder.create(
                instance -> instance.group(
                                ResourceLocation.CODEC.fieldOf("type").forGetter(Entry::attribute),
                                AttributeModifier.MAP_CODEC.forGetter(Entry::modifier))
                        .apply(instance, Entry::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, Entry>
                STREAM_CODEC = StreamCodec.composite(
                ResourceLocation.STREAM_CODEC,
                Entry::attribute,
                AttributeModifier.STREAM_CODEC,
                Entry::modifier,
                Entry::new
        );
    }
}
