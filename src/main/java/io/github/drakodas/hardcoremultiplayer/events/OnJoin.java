package io.github.drakodas.hardcoremultiplayer.events;

import io.github.drakodas.hardcoremultiplayer.HardcoreMultiplayer;
import io.github.drakodas.hardcoremultiplayer.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnJoin implements Listener{

    //Event: Player joins server
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){
        //Getting player from event
        Player player = event.getPlayer();

        //Assigning lives to player

        //If the lives hashmap does not include the player
        //Get lives from file
        if (!Utils.lives.containsKey(player.getUniqueId())){

            if (!HardcoreMultiplayer.INSTANCE.getLivesConfig().contains(player.getUniqueId().toString())) {
                Utils.lives.put(player.getUniqueId(), HardcoreMultiplayer.INSTANCE.getConfig().getInt("lives"));
            } else {
                Utils.lives.put(player.getUniqueId(), Utils.getConfigLives(player));
            }
        }

        //Sets the gamemode based on how many lives a player has
        //More than 0: Survival
        //0 or less: Spectator
        if (Utils.getLives(player) > 0) {
            player.setGameMode(GameMode.SURVIVAL);
            player.setPlayerListName(player.getName() +" "+ Utils.lives.get(player.getUniqueId())+"§4♥" );
        } else {
            player.setGameMode(GameMode.SPECTATOR);
            player.setPlayerListName(player.getName() +" §4DEAD" );
        }
    }

}