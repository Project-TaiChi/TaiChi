package org.taichi.data.provider;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ItemDataContext {

    public record ItemData(Supplier<? extends Item> itemSupplier, String name, @Nullable String chineseTranslation,
                           @Nullable String englishTranslation, List<TagKey<Item>> tags) {
    }

    public static List<ItemData> ITEM_DATAS = new ArrayList<>();
}
