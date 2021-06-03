package io.github.drakodas.hardcoremultiplayer.events;

import io.github.drakodas.hardcoremultiplayer.HardcoreMultiplayer;
import io.github.drakodas.hardcoremultiplayer.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;
import org.bukkit.event.player.PlayerJoinEvent;

public class OnSleep implements Listener
{

    //Event: Player wants to sleep
    @EventHandler
    public void onPlayerSleep(PlayerBedEnterEvent event)
    {
        //Getting config
        FileConfiguration conf = HardcoreMultiplayer.INSTANCE.getConfig();

        //Getting player from event
        Player player = event.getPlayer();

        //Checking config
        if(!conf.getBoolean("one_player_sleep"))
        {
            return;
        }

        //Skipping time to morning & setting weather to clear
        if(event.getBedEnterResult() == PlayerBedEnterEvent.BedEnterResult.OK)
        {
            player.getWorld().setTime(0);
            player.getWorld().setThundering(false);
            HardcoreMultiplayer.INSTANCE.message(player.getDisplayName() + " went to bed");
        }
    }
}