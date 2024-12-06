package org.taichi.init;

import com.mojang.serialization.Codec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.taichi.TaiChiMod;
import org.taichi.accessories.AccessoryType;

import java.util.function.Supplier;

public final class TaiAttachments {

    private static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, TaiChiMod.MOD_ID);

    public static final Supplier<AttachmentType<AccessoryType>> PLAYER_ACCESSORY = ATTACHMENTS.register(
            "player_accessory",
            () -> AttachmentType.builder(() -> AccessoryType.NONE)
                    .serialize(AccessoryType.CODEC)
                    .copyOnDeath()
                    .build()
    );

    public static final Supplier<AttachmentType<Integer>> PLAYER_AMENDMENT = ATTACHMENTS.register(
            "player_amendment",
            () -> AttachmentType.builder(() -> 0)
                    .serialize(Codec.INT)
                    .copyOnDeath()
                    .build()
    );

    public static final Supplier<AttachmentType<Integer>> PLAYER_WARP = ATTACHMENTS.register(
            "player_warp",
            () -> AttachmentType.builder(() -> 0)
                    .serialize(Codec.INT)
                    .copyOnDeath()
                    .build()
    );

    public static void init(IEventBus modbus) {
        ATTACHMENTS.register(modbus);
    }

    private TaiAttachments() {
    }
}
