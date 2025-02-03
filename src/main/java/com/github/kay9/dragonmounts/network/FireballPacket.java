package com.github.kay9.dragonmounts.network;

import com.github.kay9.dragonmounts.dragon.ai.KeybindUsingMount;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class FireballPacket {

    // Default constructor
    public FireballPacket() {}

    // Encoding the packet (no data to encode in this case)
    public static void encode(FireballPacket msg, FriendlyByteBuf buf) {
        // No data to encode, so this can be empty
    }

    // Decoding the packet (no data to decode in this case)
    public static FireballPacket decode(FriendlyByteBuf buf) {
        return new FireballPacket();
    }

    // Handling the packet (triggering the fireball logic)
    public static void handle(FireballPacket msg, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender(); // Get the player who sent the packet
            if (player != null) {
                // Check if the player is riding an entity that implements KeybindUsingMount
                if (player.getVehicle() instanceof KeybindUsingMount mount) {
                    // Call onKeyPacket method on the entity the player is riding (which will handle fireball logic)
                    mount.onKeyPacket(player); // Pass the player as the key presser
                }
            }
        });
        ctx.get().setPacketHandled(true); // Mark the packet as handled
    }
}
