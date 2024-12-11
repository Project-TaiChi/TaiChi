package org.taichi.utils.builder;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.data.loading.DatagenModLoader;
import net.neoforged.neoforge.registries.DeferredItem;
import org.taichi.data.provider.ItemDataContext;
import org.taichi.init.TaiCapabilities;
import org.taichi.init.TaiCreativeTabs;
import org.taichi.init.TaiItems;
import org.taichi.init.TaiTab;
import org.taichi.item.TaiBaseItem;
import top.theillusivec4.curios.api.CurioAttributeModifiers;
import top.theillusivec4.curios.common.CuriosRegistry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class TaiItemBuilder<T extends Item> {
    private final String name;
    private Function<Item.Properties, T> factory;
    private Consumer<Item.Properties> propertiesConsumer;
    private TaiTab tab = TaiTab.Ingredients;
    private String chineseTranslation = null;
    private String englishTranslation = null;
    private TaiCurioBuilder curioBuilder = null;
    private final List<TagKey<Item>> tags = new ArrayList<>();

    record TaiCurioAttributeModifiersBuilderEntry(String slot, Consumer<TaiCurioAttributeModifiersBuilder> attributes) {
    }

    private final List<TaiCurioAttributeModifiersBuilderEntry> modifiersBuilderEntries = new ArrayList<>();

    private final Lazy<Boolean> skipDataGens = Lazy.of(() -> !DatagenModLoader.isRunningDataGen());

    public TaiItemBuilder(String name) {
        this.name = name;
    }


    public <I extends Item> TaiItemBuilder<I> factory(Function<Item.Properties, I> factory) {
        //noinspection unchecked
        this.factory = (Function<Item.Properties, T>) factory;
        //noinspection unchecked
        return (TaiItemBuilder<I>) this;
    }

    public TaiItemBuilder<T> properties(Consumer<Item.Properties> properties) {
        this.propertiesConsumer = properties;
        return this;
    }

    public TaiItemBuilder<T> tab(TaiTab tab) {
        this.tab = tab;
        return this;
    }

    public TaiItemBuilder<T> attributes(String slot, Consumer<TaiCurioAttributeModifiersBuilder> attributes) {
        modifiersBuilderEntries.add(new TaiCurioAttributeModifiersBuilderEntry(slot, attributes));
        return this;
    }

    public TaiItemBuilder<T> textChinese(String chineseTranslation) {
        if (skipDataGens.get()) {
            return this;
        }
        this.chineseTranslation = chineseTranslation;
        return this;
    }

    public TaiItemBuilder<T> textEnglish(String englishTranslation) {
        if (skipDataGens.get()) {
            return this;
        }
        this.englishTranslation = englishTranslation;
        return this;
    }


    public TaiItemBuilder<T> tag(TagKey<Item> tag) {
        if (skipDataGens.get()) {
            return this;
        }
        this.tags.add(tag);
        return this;
    }


    public TaiItemBuilder<T> curio(Consumer<TaiCurioBuilder> curioBuilderConsumer) {
        TaiCurioBuilder builder = new TaiCurioBuilder();
        curioBuilderConsumer.accept(builder);
        this.curioBuilder = builder;
        return this;
    }


    public static TaiItemBuilder<TaiBaseItem> create(String name) {
        return new TaiItemBuilder<>(name).factory(TaiBaseItem::new);
    }


    private Item.Properties makeProperties() {
        Item.Properties properties = new Item.Properties();
        CurioAttributeModifiers.Builder curioAttributes = CurioAttributeModifiers.builder();
        for (TaiCurioAttributeModifiersBuilderEntry entry : modifiersBuilderEntries) {
            TaiCurioAttributeModifiersBuilder wrapper = new TaiCurioAttributeModifiersBuilder(name, curioAttributes, entry.slot);
            entry.attributes.accept(wrapper);
            wrapper.build();
        }
        properties.component(CuriosRegistry.CURIO_ATTRIBUTE_MODIFIERS, curioAttributes.build());
        if (propertiesConsumer != null) {
            propertiesConsumer.accept(properties);
        }
        return properties;
    }

    private void addDataGens(DeferredItem<T> item) {
        if (skipDataGens.get()) {
            return;
        }

        ItemDataContext.ItemData itemData = new ItemDataContext.ItemData(
                item,
                name,
                chineseTranslation,
                englishTranslation,
                tags
        );

        ItemDataContext.ITEM_DATAS.add(itemData);

    }


    public DeferredItem<T> register() {
        DeferredItem<T> item = TaiItems.ITEMS.register(name, () -> factory.apply(makeProperties()));
        TaiCreativeTabs.add(tab, item::asItem);
        if (curioBuilder != null) {
            TaiCapabilities.curioFactories.put(item.getId(), curioBuilder.build());
        }
        addDataGens(item);
        return item;
    }

}
