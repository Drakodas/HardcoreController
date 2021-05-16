package io.github.drakodas.hardcoremultiplayer.commands;

import io.github.drakodas.hardcoremultiplayer.HardcoreMultiplayer;
import io.github.drakodas.hardcoremultiplayer.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import javax.swing.*;

public class AddLives implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Variables
        Player player;
        int amount;

        //Getting config
        FileConfiguration conf = HardcoreMultiplayer.INSTANCE.getConfig();

        //Check for permissions
        if(sender.hasPermission("io.github.drakodas.hardcoremultiplayer.revive")){
            //Check if an player and an amount was transmitted
            if(args.length == 0)
            {
                player = (Player) sender;
                amount = 1;
            }
            else
            {
                try {
                    player = Bukkit.getPlayer(args[0]);
                }
                catch(NullPointerException npe) {
                    sender.sendMessage(HardcoreMultiplayer.PREFIX + "Player doesn't exist!");
                    return true;
                }
                amount = Integer.parseInt(args[1]);
                if(amount > conf.getInt("lives"))
                {
                    amount = 1;
                }
            }


            if(Utils.getLives(player) == conf.getInt("lives")) {
                player.sendTitle("§4Failed", "You already have the maximum lives", 10,35, 10);
                return false;
            }
            else
            {
                Utils.setLives(player, amount + Utils.getLives(player));
                player.sendTitle("§4You recieved a live", "Lives recieved: " + amount, 10,35, 10);
                player.setPlayerListName(player.getName() + " " + Utils.lives.get(player.getUniqueId()) + "§4♥");
            }
            HardcoreMultiplayer.INSTANCE.saveHashmapData();
        }
        else
        {
            sender.sendMessage(HardcoreMultiplayer.PREFIX + "Missing Permissions");
        }
        return true;
    }
}
