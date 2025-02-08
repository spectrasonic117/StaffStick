package com.spectrasonic.StaffStick;

import co.aikar.commands.BukkitCommandManager;
import com.spectrasonic.StaffStick.Commands.StaffStickCommand;
import com.spectrasonic.StaffStick.Listeners.PlayerInteractListener;
import com.spectrasonic.StaffStick.Listeners.PlayerMoveListener;
import lombok.Getter;
import com.spectrasonic.StaffStick.Utils.MessageUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    @Override
    public void onEnable() {

        instance = this;
        registerCommands();
        registerEvents();
        MessageUtils.sendStartupMessage(this);

    }

    @Override
    public void onDisable() {
        MessageUtils.sendShutdownMessage(this);
    }

    public void registerCommands() {
        BukkitCommandManager commandManager = new BukkitCommandManager(this);
        commandManager.registerCommand(new StaffStickCommand());
    }

    public void registerEvents() {
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
    }
}
