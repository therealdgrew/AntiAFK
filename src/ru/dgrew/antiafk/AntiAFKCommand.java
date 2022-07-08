package ru.dgrew.antiafk;

import world.bentobox.bentobox.api.commands.*;
import world.bentobox.bentobox.api.user.User;
import world.bentobox.bentobox.database.objects.Island;
import world.bentobox.bentobox.util.Util;

import java.util.List;

public class AntiAFKCommand extends CompositeCommand {
    private String invalid = " ";
    private String prefix = " ";
    private List<String> blockList;
    private String list = "";
    public AntiAFKCommand(AntiAFK addon)
    {
        super(addon, "antiafk", "aafk");
        this.prefix = addon.getPrefix();
        this.blockList = addon.getBlocklist();
        this.invalid = addon.getInvalid();
    }
    @Override
    public void setup()
    {
        this.setPermission("antiafk.admin");
        this.setOnlyPlayer(true);
    }
    @Override
    public boolean canExecute(User user, String label, List<String> args)
    {
        if (!Util.getWorld(user.getWorld()).getName().equals("bskyblock_world")) {
            user.sendMessage(prefix + invalid);
            return false;
        }
        Island island = getIslands().getIslandAt(user.getLocation()).orElse(null);
        if (island == null) return false;
        return true;
    }
    @Override
    public boolean execute(User user, String label, List<String> args)
    {
        User sender = user;
        if (args.size() == 0) {
            for (final String s : blockList) list += s + ", ";
            list = list.substring(0, list.length() - 2);
            sender.sendMessage(prefix + "AntiAFK Addon (by DGrew)");
            sender.sendMessage(prefix + "Whitelisted blocks: " + list);
            list = "";
            return true;
        }
        else sender.sendMessage(prefix + "Unknown command! Type /aafk to see all available commands!");
        return true;
    }
}
