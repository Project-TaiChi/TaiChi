package org.taichi.accessories;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.taichi.ModConstants;

import java.util.function.Supplier;

public final class TaiAttachments {

    private static final DeferredRegister<AttachmentType<?>> ATTACHMENTS = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, ModConstants.MOD_ID);

    public static final Supplier<AttachmentType<AccessoryType>> PLAYER_ACCESSORY = ATTACHMENTS.register(
            "player_accessory",
            () -> AttachmentType.builder(() -> (AccessoryType) null)
                    .serialize(AccessoryType.CODEC)
                    .copyOnDeath()
                    .build()
    );

    public static void init(IEventBus modbus) {
        ATTACHMENTS.register(modbus);
    }

    private TaiAttachments() {
    }
}
