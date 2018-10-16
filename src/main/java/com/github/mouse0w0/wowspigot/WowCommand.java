package com.github.mouse0w0.wowspigot;

import com.github.mouse0w0.wow.profile.User;
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
        return true;
    }

    private void help(CommandSender sender) {

    }

    private void profile(CommandSender sender) {
        if(sender instanceof Player) {
            User user = WowSpigot.getUser((Player) sender);
            sender.sendMessage("Wow IsInitialized: " + (user != null) + " Version: " + (user == null ? 0 : user.getVersion()));
        }
    }
}
