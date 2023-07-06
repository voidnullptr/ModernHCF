package dev.stackz.modernhcf.faction;

import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Faction {
    private static ArrayList<Object> factions = new ArrayList<>();
    private final String name;
    private final int points;
    private final double balance;
    private final UUID leader;
    private final UUID[] coleaders;
    private final UUID[] captains;
    private final UUID[] members;

    public Faction(String name, int points, double balance, UUID leader, UUID[] coleaders, UUID[] captains, UUID[] members) {
        this.name = name;
        this.points = points;
        this.balance = balance;
        this.leader = leader;
        this.coleaders = coleaders;
        this.captains = captains;
        this.members = members;
    }

    public static void loadFactions() {
        File file = new File("factions.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (String key : config.getKeys(false)) {
            UUID leader = UUID.fromString(config.getString(key + ".leader"));
            UUID[] coleaders = getUUIDArray(config.getStringList(key + ".coleaders"));
            UUID[] captains = getUUIDArray(config.getStringList(key + ".captains"));
            UUID[] members = getUUIDArray(config.getStringList(key + ".members"));
            int points = config.getInt(key + ".points");
            double balance = config.getDouble(key + ".balance");
            String name = config.getString(key + ".name");
            Faction faction = new Faction(name, points, balance, leader, coleaders, captains, members);
            factions.add(faction);
        }
        System.out.println(factions);
    }


    private static UUID[] getUUIDArray(List<String> list) {
        UUID[] uuids = new UUID[list.size()];
        for (int i = 0; i < list.size(); i++) {
            uuids[i] = UUID.fromString(list.get(i));
        }
        return uuids;
    }

    public static void saveFactionFile() {
        File file = new File("factions.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        for (Object obj : factions) {
            if (obj instanceof Faction) {
                Faction faction = (Faction) obj;
                String key = faction.leader.toString(); // assuming leader's UUID is unique
                config.set(key + ".leader", faction.leader.toString());
                config.set(key + ".coleaders", Arrays.asList(faction.coleaders));
                config.set(key + ".captains", Arrays.asList(faction.captains));
                config.set(key + ".members", Arrays.asList(faction.members));
                config.set(key + ".points", faction.points);
                config.set(key + ".balance", faction.balance);
                config.set(key + "..name", faction.name);
            }
        }
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static Faction createFaction(UUID leader, String name) {
        Faction faction = new Faction(name, 0, 0D, leader, new UUID[0], new UUID[0], new UUID[0]);
        factions.add(faction);
        return faction;
    }

    public static Faction getPlayersFaction(UUID player) {
        for (Object obj : factions) {
            if (obj instanceof Faction) {
                Faction faction = (Faction) obj;
                if (faction.leader.equals(player) ||
                        Arrays.asList(faction.coleaders).contains(player) ||
                        Arrays.asList(faction.captains).contains(player) ||
                        Arrays.asList(faction.members).contains(player)) {
                    return faction;
                }
            }
        }
        return null;
    }

    public static void deleteFaction(UUID leader) {
        Faction factionToDelete = null;
        for (Object obj : factions) {
            if (obj instanceof Faction) {
                Faction faction = (Faction) obj;
                if (faction.leader.equals(leader)) {
                    factionToDelete = faction;
                    break;
                }
            }
        }

        if (factionToDelete != null) {
            factions.remove(factionToDelete);

            saveFactionFile();
        } else {
            System.out.println("No faction found with the specified leader.");
        }
    }
}
