package ru.dgrew.antiafk;

import world.bentobox.bentobox.api.configuration.*;

import java.util.List;

@StoreAt(filename= "config.yml", path="addons/AntiAFK")
public class Settings implements ConfigObject {
    @ConfigComment("IslandFlyAddon Configuration [version]")
    @ConfigComment("General settings")
    @ConfigComment("This allows you to change the messages prefix.")

    @ConfigEntry(path = "general.prefix")
    private String prefix = "§a§lAntiAFK §8| §f";
    public String getPrefix() { return prefix; }
    public void setPrefix(String prefix) { this.prefix = prefix; }
    @ConfigComment("This allows you to change the invalid location message.")
    @ConfigEntry(path = "general.invalid")
    private String invalidmsg = "§cThis command cannot be run here!";
    public String getInvalidmsg() { return invalidmsg; }
    public void setInvalidmsg(String invalidmsg) { this.invalidmsg = invalidmsg; }
    @ConfigComment("This allows you to change the hunger level that prohibits the player from mining/fishing.")
    @ConfigEntry(path = "general.min-food-level")
    private int minfoodlevel = 6;
    public int getMinfoodlevel() { return minfoodlevel; }
    public void setMinfoodlevel(int minfoodlevel) { this.minfoodlevel = minfoodlevel; }
    @ConfigComment("Mining settings")
    @ConfigComment("This allows you to change the amount of blocks required to be broken to force a food level decrease.")
    @ConfigEntry(path = "block-breaking.amount")
    private int blockamount = 28;
    public int getBlockamount() { return blockamount; }
    public void setBlockamount(int blockAmount) { this.blockamount = blockAmount; }
    @ConfigComment("This allows you to change the mining limit message.")
    @ConfigEntry(path = "block-breaking.message")
    private String farmmsg = "§fYou are tired! Eat something to be able to break blocks.";
    public String getFarmmsg() { return farmmsg; }
    public void setFarmmsg(String farmmsg) { this.farmmsg = farmmsg; }
    @ConfigComment("This list allows you to add blocks to a whitelist, meaning you can allow players to break them while hungry.")
    @ConfigComment("By default this includes crops, leaves and fire so players wouldn't be stuck with no hunger.")
    @ConfigComment("Please be sure to properly format the list with Material item IDs!")
    @ConfigComment("You can look up the latest Material list here: https://hub.spigotmc.org/javadocs/spigot/org/bukkit/Material.html")
    @ConfigEntry(path = "block-breaking.block-list")
    private List<String> blockList;
    public List<String> getBlockList() { return blockList; }
    public void setBlockList(List<String> blockList) { this.blockList = blockList; }
    @ConfigComment("Fishing settings")
    @ConfigComment("This allows you to change the amount of fish required to be caught to force a food level decrease.")
    @ConfigEntry(path = "fising.amount")
    private int caughtfishes = 5;
    public int getCaughtfishes() { return caughtfishes; }
    public void setCaughtfishes(int caughtfishes) { this.caughtfishes = caughtfishes; }
    @ConfigComment("This allows you to change the fishing limit message.")
    @ConfigEntry(path = "fishing.message")
    private String fishmsg = "§fYou are tired! Eat something to be able to fish.";
    public String getFishmsg() { return fishmsg; }
    public void setFishmsg(String fishmsg) { this.fishmsg = fishmsg; }
}
