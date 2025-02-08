package com.spectrasonic.StaffStick.Listeners;

import com.spectrasonic.StaffStick.Managers.FreezeManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        if (FreezeManager.isPlayerFrozen(event.getPlayer())) {
            // Prevent the player from moving but can look around
            if (event.getFrom().getX() != event.getTo().getX() || event.getFrom().getZ() != event.getTo().getZ()) {
                event.setTo(event.getFrom());
            }
        }
    }
}