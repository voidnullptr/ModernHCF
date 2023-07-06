package dev.stackz.modernhcf;

import dev.stackz.modernhcf.faction.Command;
import dev.stackz.modernhcf.listener.LegacyCombat;
import org.bukkit.plugin.java.JavaPlugin;

import static dev.stackz.modernhcf.faction.Faction.loadFactions;
import static dev.stackz.modernhcf.faction.Faction.saveFactionFile;

public final class ModernHCF extends JavaPlugin {

    @Override
    public void onEnable() {
        loadFactions();
        saveDefaultConfig();

        this.getServer().getPluginManager().registerEvents(new LegacyCombat(), this);
        this.getCommand("faction").setExecutor(new Command());
    }

    @Override
    public void onDisable() {

    }
}
