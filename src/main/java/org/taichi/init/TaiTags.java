package org.taichi.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.Item;
import org.taichi.TaiChiMod;

public class TaiTags {
    public static class CuriosTags {

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

    public static class DamageTypes {
        public static final TagKey<DamageType> IS_VECTOR = TagKey.create(Registries.DAMAGE_TYPE, TaiChiMod.loc("is_vector"));
        public static final TagKey<DamageType> IS_STAR_BRACELET_IMMUNE = TagKey.create(Registries.DAMAGE_TYPE, TaiChiMod.loc("is_star_bracelet_immune"));
    }
}
