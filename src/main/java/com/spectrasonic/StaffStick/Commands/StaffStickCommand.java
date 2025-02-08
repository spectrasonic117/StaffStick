package com.spectrasonic.StaffStick.Commands;

import com.spectrasonic.StaffStick.Main;
import com.spectrasonic.StaffStick.Utils.ItemBuilder;
import com.spectrasonic.StaffStick.Utils.MessageUtils;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Subcommand;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

@CommandAlias("staffstick|ss")
@CommandPermission("staffstick.give")
public class StaffStickCommand extends BaseCommand {

    @Default
    public void onDefault(Player sender) {
        MessageUtils.sendMessage(sender, "<yellow>Usage: /staffstick give <player>");
    }

    @Subcommand("give")
    @CommandCompletion("@players")
    public void onGive(Player sender, String[] args) {
        if (args.length < 1) {
            MessageUtils.sendMessage(sender, "<yellow>Usage: /staffstick give <player>");
            return;
        }

        Player target = getPlayer(args[0]);
        if (target == null) {
            MessageUtils.sendMessage(sender, "<red>Player not found.");
            return;
        }

        var StaffStickItem = ItemBuilder.setMaterial("BLAZE_ROD")
                .setName("<gradient:#FBBA00:#FF8714>Staff Stick")
                .setLore("Gives you the ability to freeze players.",
                        "Right click to freeze a player.",
                        "Right click again to unfreeze a player.")
                .setCustomModelData(1000)
                .build();
        target.getInventory().addItem(StaffStickItem);
        MessageUtils.sendMessage(sender, "<green>Staff Stick given to <yellow>" + target.getName());
    }

    private Player getPlayer(String name) {
        return Main.getInstance().getServer().getPlayer(name);
    }

    @CommandCompletion("@players")
    public List<String> complete(String[] args) {
        if (args.length == 2) {
            return Main.getInstance().getServer().getOnlinePlayers().stream()
                    .map(Player::getName)
                    .filter(name -> name.startsWith(args[1]))
                    .collect(Collectors.toList());
        }
        return List.of();
    }
}