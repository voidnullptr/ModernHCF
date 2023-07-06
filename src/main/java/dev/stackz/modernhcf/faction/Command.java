package dev.stackz.modernhcf.faction;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static dev.stackz.modernhcf.faction.Faction.*;

public class Command implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String s, String[] args) {
        Player p = (Player) sender;

        if (!(sender instanceof Player)) {
            sender.sendMessage("You must be a player to use this command.");
            return true;
        }

        if (args.length < 1) {
            p.sendMessage("You need to specify a command.");
            return true;
        }

        switch (args[0]) {
            case "create":
                if (args.length < 2) {
                    p.sendMessage("You need to specify a faction name.");
                    return true;
                }
                if (getPlayersFaction(p.getUniqueId()) == null) {
                    createFaction(p.getUniqueId(), args[1]);
                    saveFactionFile();
                    p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eFaction has been created successfully."));
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&eThe faction "
                            + "&9" + args[1] + " &ehas been created by " + "&9" + p.getName().toString()));
                    return true;
                }
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou are already in a faction!"));
            case "disband":
                if (getPlayersFaction(p.getUniqueId()) != null) {
                    deleteFaction(p.getUniqueId());
                    saveFactionFile();
                    Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', "&eThe faction "
                            + "&9" + getPlayersFaction(p.getUniqueId()) + "  &ehas been disbanded!"));
                    return true;
                }
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&eYou are not in a faction."));
        }
        return true;
    }
}
