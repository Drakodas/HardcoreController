package io.github.drakodas.hardcoremultiplayer;

import io.github.drakodas.hardcoremultiplayer.commands.Revive;
import io.github.drakodas.hardcoremultiplayer.commands.Test;
import io.github.drakodas.hardcoremultiplayer.events.OnDeath;
import io.github.drakodas.hardcoremultiplayer.events.OnJoin;
import io.github.drakodas.hardcoremultiplayer.events.OnRevive;
import io.github.drakodas.hardcoremultiplayer.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.print.attribute.ResolutionSyntax;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public final class HardcoreMultiplayer extends JavaPlugin {

    public static String PREFIX = "§4§nHardMP§0: §7";
    public static HardcoreMultiplayer INSTANCE;
    private String path;
    private File livesFile;
    private FileConfiguration livesConfig;
    private File gravesFile;
    private FileConfiguration graveConfig;

    public HardcoreMultiplayer()
    {
        INSTANCE = this;
    }

    //Plugin startup logic
    @Override
    public void onEnable() {
        this.register();
        this.createFiles();
        this.saveDefaultConfig();
        this.loadArmorstands();
        log("Plugin loaded");
    }

    //Plugin shutdown logic
    @Override
    public void onDisable() {
        saveHashmapData();
        log("Plugin unloaded");
    }

    //Log - Method
    //Logs provided text to the console
    public void log(String text) {
        Bukkit.getConsoleSender().sendMessage(PREFIX + text);
    }

    //Register - Method
    //Registers Events and commands
    private void register() {
        //Registers Events
        PluginManager pluginManager = Bukkit.getPluginManager();
        pluginManager.registerEvents(new OnJoin(), this);
        pluginManager.registerEvents(new OnDeath(), this);
        pluginManager.registerEvents(new OnRevive(), this);

        //Registers Commands
        Objects.requireNonNull(Bukkit.getPluginCommand("revive")).setExecutor(new Revive());
        Objects.requireNonNull(Bukkit.getPluginCommand("test")).setExecutor(new Test());
    }

    public static HardcoreMultiplayer get() {
        return INSTANCE;
    }

    public FileConfiguration getLivesConfig() {
        return this.livesConfig;
    }

    public FileConfiguration getGraveConfig() {
        return this.graveConfig;
    }

    private void createFiles() {
        this.livesFile = new File(this.getDataFolder(), "lives.yml");
        if (!this.livesFile.exists()) {
            this.livesFile.getParentFile().mkdirs();
            this.saveResource("lives.yml", false);
        }

        this.livesConfig = new YamlConfiguration();

        try {
            this.livesConfig.load(this.livesFile);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }

        this.gravesFile = new File(this.getDataFolder(), "graves.yml");
        if (!this.gravesFile.exists()) {
            this.gravesFile.getParentFile().mkdirs();
            this.saveResource("graves.yml", false);
        }

        this.graveConfig = new YamlConfiguration();

        try {
            this.graveConfig.load(this.gravesFile);
        } catch (InvalidConfigurationException | IOException e) {
            e.printStackTrace();
        }
    }

    public void saveHashmapData() {
        Iterator var1 = Utils.lives.entrySet().iterator();

        Map.Entry entry;
        while(var1.hasNext()) {
            entry = (Map.Entry)var1.next();
            this.livesConfig.set(entry.getKey() + ".lives", entry.getValue());
        }

        var1 = Utils.graveArmorstand.entrySet().iterator();

        while(var1.hasNext()) {
            entry = (Map.Entry)var1.next();
            this.graveConfig.set(entry.getKey() + ".armorstand", entry.getValue());
        }

        try {
            this.livesConfig.save(this.livesFile);
            this.graveConfig.save(this.gravesFile);
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    //LoadArmorstands - Method
    //Loads all grave-armorstands and corrects the modifiers
    public void loadArmorstands()
    {
        //Loops through the graves and modifies the armorstand
        for(String str: getGraveConfig().getConfigurationSection("").getKeys(false))
        {
            ArmorStand armorStand = (ArmorStand) Utils.getEntityFromUUID((UUID)getGraveConfig().get(str));
            armorStand.setInvulnerable(true);
            armorStand.setArms(true);
            armorStand.setBasePlate(false);
            armorStand.setCanMove(false);
            armorStand.setCollidable(false);
            armorStand.setGliding(false);
            armorStand.setGlowing(true);
        }
    }
}
