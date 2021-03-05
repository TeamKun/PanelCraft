package net.kunmc.lab.panelcraft.commands;

import org.bukkit.command.CommandSender;

import java.util.List;

public interface CommandBase {

    String getName();
    boolean onCommand(CommandSender sender, String[] args);
    List<String> onTabComplete(String[] args);

}
