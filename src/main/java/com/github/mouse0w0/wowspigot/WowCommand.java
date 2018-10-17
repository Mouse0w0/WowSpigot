package com.github.mouse0w0.wowspigot;

import com.github.mouse0w0.wow.profile.User;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WowCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            help(sender);
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "help":
                help(sender);
                return true;
            case "profile":
                profile(sender);
                return true;
        }
        sender.sendMessage(ChatColor.RED + "未知的指令，请输入/help查看帮助。");
        return true;
    }

    private void help(CommandSender sender) {
        sender.sendMessage( "/wow profile 查看当前玩家的Wow数据。");
    }

    private void profile(CommandSender sender) {
        if(sender instanceof Player) {
            if(!sender.hasPermission("wow.profile"))
                return;
            User user = WowSpigot.getUser((Player) sender);
            sender.sendMessage("Wow IsInitialized: " + (user != null) + " Version: " + (user == null ? 0 : user.getVersion()));
        }
    }
}
