package org.taichi.accessories;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

/**
 * 饰品类型
 */
public enum AccessoryType implements StringRepresentable {


    /*
     * 暗属性
     */
    DARK,
    /*
     * 光属性
     */
    LIGHT;

    public boolean support(AccessoryType type) {
        return type == this;
    }

    public static final Codec<AccessoryType> CODEC = StringRepresentable.fromEnum(AccessoryType::values);

    @Override
    public @NotNull String getSerializedName() {
        return name();
    }
}
