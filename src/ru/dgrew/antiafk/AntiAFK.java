package ru.dgrew.antiafk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerFishEvent;

import world.bentobox.bentobox.api.addons.Addon;
import world.bentobox.bentobox.api.configuration.Config;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.bentobox.database.objects.Island;

public class AntiAFK extends Addon implements Listener {
    private Settings settings;
    private final Map<User, Integer> playerBlockCounter = new HashMap<>();
    private final Map<User, Integer> playerFishingCount = new HashMap<>();
    private String prefix;
    private int blockCount;
    private int fishingCount;
    private List<String> blockList;
    private int minFoodlevel;
    private String blockmsg;
    private String fishmsg;
    private String invalid;

    @Override
    public void onLoad() {
        super.onLoad();
        this.saveDefaultConfig();
        this.settings = new Config<>(this, Settings.class).loadConfigObject();
        if (this.settings == null) {
            this.logError("AntiAFK settings could not load! Addon disabled.");
            this.setState(State.DISABLED);
        }
    }
    @Override
    public void onEnable() {
        setUpSettings();
        this.registerListener(this);
        new AntiAFKCommand(this);
        getLogger().info( this.getDescription().getName() + " version " + this.getDescription().getVersion() + " enabled successfully!");
    }
    @Override
    public void onDisable() {
        if (this.settings == null) new Config<>(this, Settings.class).saveConfigObject(this.settings);
        getLogger().info(this.getDescription().getName() + " version " + this.getDescription().getVersion() + " disabled successfully!");
    }
    @Override
    public void onReload() {
        super.onReload();
        this.settings = new Config<>(this, Settings.class).loadConfigObject();
        if (this.settings == null) {
            this.logError("AntiAFK settings could not load! Addon disabled.");
            this.setState(State.DISABLED);
        }
    }
    public void setUpSettings() {
        blockList = this.settings.getBlockList();
        prefix = this.settings.getPrefix();
        blockCount = this.settings.getBlockamount();
        fishingCount = this.settings.getCaughtfishes();
        minFoodlevel = this.settings.getMinfoodlevel();
        blockmsg = this.settings.getFarmmsg();
        fishmsg = this.settings.getFishmsg();
        invalid = this.settings.getInvalidmsg();
    }
    @EventHandler
    public void onBlockBreaking(BlockBreakEvent e) {
        User u = User.getInstance(e.getPlayer());
        if (u.hasPermission("antiafk.bypass")) { e.setCancelled(false); return; }
        Island island = getIslands().getIslandAt(u.getLocation()).orElse(null);
        if (island != null) if (!island.getMemberSet().contains(u.getUniqueId())) { e.setCancelled(true); return; }
        for(String s : blockList) {
            if (e.getBlock().getType().equals(Material.getMaterial(s))) {
                e.setCancelled(false);
                return;
            }
        }
        if (u.getPlayer().getFoodLevel() <= this.minFoodlevel) {
            e.setCancelled(true);
            u.sendMessage(prefix + blockmsg);
            return;
        }
        Integer blocks = this.playerBlockCounter.getOrDefault(u, 0);
        blocks = blocks + 1;
        if (blocks >= this.blockCount) {
            if (u.getPlayer().getSaturation() <= 0f) u.getPlayer().setFoodLevel(u.getPlayer().getFoodLevel() - 1);
            else u.getPlayer().setSaturation(u.getPlayer().getSaturation() - 1f);
            blocks = 0;
        }
        this.playerBlockCounter.put(u, blocks);
    }
    @EventHandler
    public void onPlayerFishing(PlayerFishEvent e) {
        PlayerFishEvent.State fishingState = e.getState();
        User u = User.getInstance(e.getPlayer());
        if (fishingState == PlayerFishEvent.State.FISHING) {
            if (u.getPlayer().getFoodLevel() <= this.minFoodlevel) {
                e.setCancelled(true);
                u.sendMessage(prefix + fishmsg);
            }
        } else if ((fishingState == PlayerFishEvent.State.CAUGHT_ENTITY || fishingState == PlayerFishEvent.State.CAUGHT_FISH)) {
            Integer fishedEntities = this.playerFishingCount.getOrDefault(u, 0);
            fishedEntities = fishedEntities + 1;
            if (fishedEntities >= this.fishingCount) {
                if (u.getPlayer().getSaturation() <= 0f) u.getPlayer().setFoodLevel(u.getPlayer().getFoodLevel() - 1);
                else u.getPlayer().setSaturation(u.getPlayer().getSaturation() - 1f);
                fishedEntities = 0;
            }
            this.playerFishingCount.put(u, fishedEntities);
        }
    }
    // getters
    public String getPrefix() { return prefix; }
    public String getInvalid() { return invalid; }
    public List<String> getBlocklist() { return blockList; }
}
