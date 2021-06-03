package io.github.drakodas.hardcoremultiplayer.events;

import io.github.drakodas.hardcoremultiplayer.HardcoreMultiplayer;
import io.github.drakodas.hardcoremultiplayer.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerLoadEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;


public class OnRefill implements Listener {

    //Event: Player rightclicks an item
    @EventHandler
    public void onPlayerRightClick(PlayerInteractEvent event) {
        //Getting player and item from event
        Player player = event.getPlayer();
        ItemStack item = event.getItem();

        //Getting config
        FileConfiguration conf = HardcoreMultiplayer.INSTANCE.getConfig();

        //If player is sneaking let them use the item
        if(!player.isSneaking()) {
            if (item != null) {
                //Looping trough all items in the config file
                for (String str : conf.getConfigurationSection("items_refill").getKeys(false)) {
                    //If the item the player uses is equal to the item in the config file
                    //Add a live
                    if (item.equals(new ItemStack(Material.getMaterial(conf.getString("items_refill." + str))))) {
                        //Commandbuilder
                        //addlives and deletes item
                        ConsoleCommandSender console = player.getServer().getConsoleSender();
                        String command = "addlives " + player.getName() + " 1";
                        if (Utils.getLives(player) < 3) {
                            Bukkit.dispatchCommand(console, command);
                            item.subtract();
                        } else {
                            Bukkit.dispatchCommand(console, command);
                        }
                        return;
                    }
                }
            }
        }
    }
}
