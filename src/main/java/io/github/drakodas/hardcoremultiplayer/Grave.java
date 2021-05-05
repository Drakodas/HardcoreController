package io.github.drakodas.hardcoremultiplayer;

import io.github.drakodas.hardcoremultiplayer.utils.Utils;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class Grave {
    //Variables
    Player player;
    World world;

    //Constructor
    public Grave(Player player)
    {
        this.player = player;
        world = player.getWorld();
    }

    //Create - Method
    //Creates two armortands, which represents the grave from the dead player,
    //one displays the name, the other saves the uuid from the player
    public void create()
    {
        //Armorstand is created
        ArmorStand armorStand = (ArmorStand) world.spawnEntity(player.getLocation(), EntityType.ARMOR_STAND);

        //Playerskull gets created
        ItemStack skull = new ItemStack(Material.valueOf("PLAYER_HEAD"));

        //Armorstand gets equipped
        armorStand.getEquipment().setHelmet(Utils.skullUUID(skull, player.getUniqueId()));
        armorStand.getEquipment().setChestplate(player.getEquipment().getChestplate());
        armorStand.getEquipment().setLeggings(player.getEquipment().getLeggings());
        armorStand.getEquipment().setBoots(player.getEquipment().getBoots());

        //Armorstand gets modified
        armorStand.setInvulnerable(true);
        armorStand.setArms(true);
        armorStand.setBasePlate(false);
        armorStand.setCanMove(false);
        armorStand.setCollidable(false);
        armorStand.setGliding(false);
        armorStand.setGlowing(true);
        armorStand.setCustomName(player.getName());
        armorStand.setCustomNameVisible(true);
        armorStand.setDisabledSlots(EquipmentSlot.HEAD,EquipmentSlot.CHEST,EquipmentSlot.LEGS,EquipmentSlot.FEET);

        //Armorstand is saved
        Utils.setGraveArmorstand(player, armorStand);
    }

    //Remove - Method
    //Removes the Grave and deletes the armorstand
    public void remove()
    {
        Utils.getGraveArmorstand(player).remove();
    }
}