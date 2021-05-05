package io.github.drakodas.hardcoremultiplayer.events;

import io.github.drakodas.hardcoremultiplayer.HardcoreMultiplayer;
import io.github.drakodas.hardcoremultiplayer.utils.FileConfig;
import io.github.drakodas.hardcoremultiplayer.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
import org.bukkit.inventory.ItemStack;


public class OnRevive implements Listener {

    //Event: Player rightclicks an armorstand
    @EventHandler
    public void onPlayerRightClick(PlayerArmorStandManipulateEvent event) {
        //Getting player and armorstand from event
        Player player = event.getPlayer();
        ArmorStand armorStand = event.getRightClicked();

        //Getting config
        FileConfiguration conf = HardcoreMultiplayer.INSTANCE.getConfig();

        //Looping trough all items in the config file
        for(String str: conf.getConfigurationSection("items_revive").getKeys(false))
        {
            //If the item the player uses is equal to the item in the config file
            //Revive dead player
            if(event.getPlayerItem().equals(new ItemStack(Material.getMaterial(conf.getString("items_revive." + str)))))
            {
                //Looping trough all online players
                for (Player pl: player.getServer().getOnlinePlayers())
                {
                    //If the targeted armorstand is also a grave armorstand
                    //Revive dead player
                    if(armorStand == Utils.getGraveArmorstand(pl))
                    {
                        ConsoleCommandSender console = player.getServer().getConsoleSender();
                        String command = "revive " + pl.getName();
                        Bukkit.dispatchCommand(console, command);
                        return;
                    }
                }
                //Player not online
                player.getServer().broadcastMessage(HardcoreMultiplayer.PREFIX + "The dead player is not online!");
            }
        }

    }
}
