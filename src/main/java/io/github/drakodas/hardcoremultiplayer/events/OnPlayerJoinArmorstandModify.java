package io.github.drakodas.hardcoremultiplayer.events;

import io.github.drakodas.hardcoremultiplayer.HardcoreMultiplayer;
import io.github.drakodas.hardcoremultiplayer.utils.Utils;
import org.bukkit.GameMode;
import org.bukkit.Server;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.server.ServerLoadEvent;

import java.util.UUID;

public class OnPlayerJoinArmorstandModify implements Listener{

    //Event: Player joins server
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){

        //Loops through the graves and modifies the armorstand
        for(String str: HardcoreMultiplayer.INSTANCE.getGraveConfig().getConfigurationSection("").getKeys(false))
        {
            String uuidString = (String) HardcoreMultiplayer.INSTANCE.getGraveConfig().get(str + ".armorstand");

            try
            {
                ArmorStand armorStand = (ArmorStand) Utils.getEntityFromUUID(UUID.fromString(uuidString));
                armorStand.setInvulnerable(true);
                armorStand.setArms(true);
                armorStand.setBasePlate(false);
                armorStand.setCanMove(false);
                armorStand.setCollidable(false);
                armorStand.setGliding(false);
                armorStand.setGlowing(true);
            }
            catch (NullPointerException e)
            {
                HardcoreMultiplayer.INSTANCE.log("An armorstand was already destroyed, this is not an error.");
            }

        }
    }

}