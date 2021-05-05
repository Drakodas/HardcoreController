package io.github.drakodas.hardcoremultiplayer.commands;

import io.github.drakodas.hardcoremultiplayer.HardcoreMultiplayer;
import io.github.drakodas.hardcoremultiplayer.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.EntityEffect;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class Revive implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        //Variables
        Player player;

        //Check for permissions
        if(sender.hasPermission("io.github.drakodas.hardcoremultiplayer.revive")){
            //Check if an player was transmitted
            if(args.length == 0)
            {
                player = (Player) sender;
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
            }


            if(Utils.getLives(player) < 1) {
                //Change gamemode
                player.setGameMode(GameMode.SURVIVAL);
                //Add live
                Utils.lives.put(player.getUniqueId(), 1);
                //Change name
                player.setPlayerListName(player.getName() + " " + Utils.lives.get(player.getUniqueId()) + "§4♥");
                //Teleport
                player.teleport(Utils.getGraveArmorstand(player).getLocation());
                //Remove armorstand
                Utils.getGraveArmorstand(player).remove();
                Utils.getGraveArmorstandName(player).remove();
                //Play totemeffect
                player.playEffect(EntityEffect.TOTEM_RESURRECT);
                return true;
            }
            else
            {
                sender.sendMessage(HardcoreMultiplayer.PREFIX + "Player isn´t dead... Yet!");
            }
        }
        else
        {
            sender.sendMessage(HardcoreMultiplayer.PREFIX + "Missing Permissions");
        }
        return true;
    }
}
