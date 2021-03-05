package net.kunmc.lab.panelcraft.commands;

import net.kunmc.lab.panelcraft.Game;
import net.kunmc.lab.panelcraft.PanelCraft;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class CommandSetup implements CommandBase {

    @Override
    public String getName() {
        return "setup";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "残念ながらこのコマンドはプレイヤーからしか対応していません...");
            return true;
        }

        if (PanelCraft.game != null && PanelCraft.game.isRunning()) {
            sender.sendMessage(ChatColor.RED + "ゲームが進行中です。");
            return true;
        }

        if (PanelCraft.game != null && !PanelCraft.game.isFinished()) {
            sender.sendMessage(ChatColor.RED + "既にセットアップ済みです。");
            return true;
        }

        int range;
        switch (args.length) {
            case 0:
                range = 27;
                break;
            case 1:
                try {
                    range = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "引数の形式が間違っているようです。");
                    return true;
                }
                break;
            default:
                sender.sendMessage(ChatColor.RED + "コマンドの引数が間違ってます。");
                return true;
        }

        Location loc = ((Player) sender).getLocation();

        PanelCraft.game = new Game(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ(), range);

        return true;
    }

    @Override
    public List<String> onTabComplete(String[] args) {
        switch (args.length) {
            case 1:
                return Collections.singletonList("<range>");
            default:
                return Collections.emptyList();
        }
    }

}
