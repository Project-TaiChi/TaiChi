package org.taichi.annotations;

import org.jetbrains.annotations.NotNull;

import javax.annotation.meta.TypeQualifierDefault;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 包注解，用于标记包内所有方法和参数为非空，主要用于IDEA的代码检查
 */
@NotNull
@Retention(RetentionPolicy.RUNTIME)
@TypeQualifierDefault({ElementType.METHOD, ElementType.PARAMETER})
public @interface NotNullByDefault {
}
