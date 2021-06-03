package io.github.drakodas.hardcoremultiplayer.utils;

import io.github.drakodas.hardcoremultiplayer.HardcoreMultiplayer;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.UUID;

public class Utils {
    public static HashMap<UUID, Integer> lives = new HashMap<UUID, Integer>();
    public static HashMap<UUID, String> graveArmorstand = new HashMap<UUID, String>();

    public Utils(){    }

    public static int getLives(Player player) {
        if (!lives.containsKey(player.getUniqueId())) {
            lives.put(player.getUniqueId(), getConfigLives(player));
        }

        return (Integer)lives.get(player.getUniqueId());
    }

    public static int getConfigLives(Player player) {
        return HardcoreMultiplayer.INSTANCE.getLivesConfig().getInt(player.getUniqueId() + ".lives");
    }

    public static void setLives(Player player, int i) {
        lives.put(player.getUniqueId(), i);
    }

    public static Entity getEntityFromUUID(UUID uuid) {
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity.getUniqueId().equals(uuid))
                    return entity;
            }
        }
        return null;
    }

    public static ArmorStand getGraveArmorstand(Player player) {
        if (!graveArmorstand.containsKey(player.getUniqueId())) {
            graveArmorstand.put(player.getUniqueId(), getConfigGraveArmorstand(player));
        }
        return (ArmorStand) getEntityFromUUID(UUID.fromString(graveArmorstand.get(player.getUniqueId())));
    }

    public static String getConfigGraveArmorstand(Player player) {
        String uuidString = HardcoreMultiplayer.INSTANCE.getGraveConfig().getString(player.getUniqueId() + ".armorstand");
        return uuidString;
    }

    public static void setGraveArmorstand(Player player, ArmorStand aS) {
        graveArmorstand.put(player.getUniqueId(), aS.getUniqueId().toString());
    }

    public static ItemStack skullUUID(ItemStack item, UUID id) {

        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(id));
        item.setItemMeta(meta);

        return item;
    }
}
