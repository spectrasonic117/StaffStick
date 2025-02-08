package com.spectrasonic.StaffStick.Managers;

import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class FreezeManager {

    private static final Set<UUID> frozenPlayers = new HashSet<>();

    public static boolean isPlayerFrozen(Player player) {
        return frozenPlayers.contains(player.getUniqueId());
    }

    public static void freezePlayer(Player player) {
        frozenPlayers.add(player.getUniqueId());
    }

    public static void unfreezePlayer(Player player) {
        frozenPlayers.remove(player.getUniqueId());
    }
}