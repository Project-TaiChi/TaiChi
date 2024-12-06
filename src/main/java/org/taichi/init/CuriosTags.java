package org.taichi.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CuriosTags {

    private static TagKey<Item> create(String name) {
        return TagKey.create(Registries.ITEM, ResourceLocation.fromNamespaceAndPath("curios", name));
    }

    public static final TagKey<Item> BACK = create("back");
    public static final TagKey<Item> BELT = create("belt");
    public static final TagKey<Item> BODY = create("body");
    public static final TagKey<Item> BRACELET = create("bracelet");
    public static final TagKey<Item> CHARM = create("charm");
    public static final TagKey<Item> CURIO = create("curio");
    public static final TagKey<Item> HANDS = create("hands");
    public static final TagKey<Item> HEAD = create("head");
    public static final TagKey<Item> NECKLACE = create("necklace");
    public static final TagKey<Item> RING = create("ring");
}
