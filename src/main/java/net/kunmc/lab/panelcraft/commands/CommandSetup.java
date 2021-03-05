package net.kunmc.lab.panelcraft.commands;

import net.kunmc.lab.panelcraft.PanelCraft;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class CommandSetup implements CommandBase {

    @Override
    public String getName() {
        return "setup";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length != 0) {
            sender.sendMessage(ChatColor.RED + "コマンドの引数が間違ってます。");
            return true;
        }

        if (PanelCraft.game == null) {
            sender.sendMessage(ChatColor.RED + "すでに終了しています。");
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(String[] args) {
        return null;
    }

}
