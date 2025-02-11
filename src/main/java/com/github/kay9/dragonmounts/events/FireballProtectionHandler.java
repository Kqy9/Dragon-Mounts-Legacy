package com.github.kay9.dragonmounts.events;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.LargeFireball;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "dragonmounts", bus = Mod.EventBusSubscriber.Bus.FORGE)
public class FireballProtectionHandler {
    @SubscribeEvent
    public static void onEntityDamage(LivingDamageEvent event) {
        if (event.getSource().getDirectEntity() instanceof LargeFireball fireball) {
            // Get the fireball's owner
            Entity owner = fireball.getOwner();

            // Check if the owner is a player AND the player is the entity being hit
            if (owner instanceof Player && owner == event.getEntity()) {
                event.setCanceled(true); // Prevent damage only for player-owned fireballs
            }
        }
    }
}
