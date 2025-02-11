package com.github.kay9.dragonmounts.events;

import com.github.kay9.dragonmounts.dragon.TameableDragon;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;

@Mod.EventBusSubscriber(modid = "dragonmounts", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class DragonDismountHandler {

    @SubscribeEvent
    public static void onPlayerLogout(PlayerEvent.PlayerLoggedOutEvent event) {
        if (event.getEntity() instanceof ServerPlayer serverPlayer) {
            // Get all TameableDragon entities in the world
            List<TameableDragon> dragons = serverPlayer.level().getEntitiesOfClass(TameableDragon.class, serverPlayer.getBoundingBox().inflate(1000));

            for (TameableDragon dragon : dragons) {
                // Ensure the dragon has an owner and check if it matches the disconnecting player
                if (dragon.getOwner() != null && dragon.getOwner().getUUID().equals(serverPlayer.getUUID())) {
                    dragon.dismountAllPassengers(); // Remove all riders from the dragon
                }
            }
        }
    }
}
