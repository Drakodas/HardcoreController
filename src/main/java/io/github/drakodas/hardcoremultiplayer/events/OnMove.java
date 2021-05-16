package io.github.drakodas.hardcoremultiplayer.events;

import io.github.drakodas.hardcoremultiplayer.HardcoreMultiplayer;
import io.github.drakodas.hardcoremultiplayer.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class OnMove implements Listener{

    //Event: Player moves
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event){
        //Getting player from event
        Player player = event.getPlayer();

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