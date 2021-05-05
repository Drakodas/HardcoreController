package io.github.drakodas.hardcoremultiplayer.events;

import io.github.drakodas.hardcoremultiplayer.Grave;
import io.github.drakodas.hardcoremultiplayer.HardcoreMultiplayer;
import io.github.drakodas.hardcoremultiplayer.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class OnDeath implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity().getPlayer();

        if (player instanceof Player)
        {
            if (Utils.getLives(player) > 1)
            {
                int newLives;
                newLives = Utils.getLives(player) - 1;
                Utils.setLives(player, newLives);
                player.setPlayerListName(player.getName() +" "+ Utils.lives.get(player.getUniqueId())+"§4♥" );
                player.sendTitle("§4YOU DIED", "Lives left: " + Utils.getLives(player), 10,35, 10);
                player.getServer().broadcastMessage(HardcoreMultiplayer.PREFIX + player.getName() + " died! They have " + Utils.getLives(player) + " left.");
                player.playSound(player.getLocation(), Sound.valueOf(HardcoreMultiplayer.INSTANCE.getConfig().getString("sound_death.sub_death")), 5, 1);
                event.setCancelled(true);
                HardcoreMultiplayer.INSTANCE.saveHashmapData();
            }
            else if (Utils.getLives(player) == 1)
            {
                int newLives;
                newLives = Utils.getLives(player) - 1;
                Utils.setLives(player, newLives);
                player.setPlayerListName(player.getName() +" §4DEAD" );
                player.setGameMode(GameMode.SPECTATOR);
                player.sendTitle("§4YOU DIED", "Other players can still revive you", 10,35, 10);
                player.getServer().broadcastMessage(HardcoreMultiplayer.PREFIX + player.getName() + " died! They can still be revived.");
                player.playSound(player.getLocation(), Sound.valueOf(HardcoreMultiplayer.INSTANCE.getConfig().getString("sound_death.death")), 5, 1);
                event.setCancelled(true);
                Grave grave = new Grave(player);
                grave.create();
                HardcoreMultiplayer.INSTANCE.saveHashmapData();
            }
        }
    }
}
