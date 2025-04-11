package com.spectrasonic.StaffStick.Listeners;

import com.spectrasonic.StaffStick.Managers.FreezeManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Player target))
            return;

        Player player = event.getPlayer();
        var itemInHand = player.getInventory().getItemInMainHand();

        if (!player.hasPermission("staffstick.staff") ||
                !itemInHand.getType().equals(Material.BLAZE_ROD) ||
                !itemInHand.hasItemMeta() ||
                itemInHand.getItemMeta().getCustomModelData() != 1000 ||
                !itemInHand.getItemMeta().hasDisplayName())
            return;

        if (FreezeManager.isPlayerFrozen(target)) {
            FreezeManager.unfreezePlayer(target);
        } else {
            FreezeManager.freezePlayer(target);
        }
    }

}