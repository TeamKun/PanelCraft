package net.kunmc.lab.panelcraft.commands;

import net.kunmc.lab.panelcraft.PanelCraft;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Collections;
import java.util.List;

public class CommandErase implements CommandBase {

    @Override
    public String getName() {
        return "erase";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (PanelCraft.game == null) {
            sender.sendMessage(ChatColor.RED + "まだゲームが開始されていません。");
            return true;
        }

        if (PanelCraft.game.isFinished()) {
            sender.sendMessage(ChatColor.RED + "既に終了しているようです...");
            return true;
        }

        PanelCraft.game.erase();
        PanelCraft.game = null;
        Bukkit.broadcastMessage(ChatColor.GREEN + "パネルが抹消されました。");

        return true;
    }

    @Override
    public List<String> onTabComplete(String[] args) {
        return Collections.emptyList();
    }

}
