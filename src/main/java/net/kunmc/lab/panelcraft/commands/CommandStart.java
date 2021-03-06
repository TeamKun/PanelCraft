package net.kunmc.lab.panelcraft.commands;

import net.kunmc.lab.panelcraft.Game;
import net.kunmc.lab.panelcraft.PanelCraft;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CommandStart implements CommandBase {

    @Override
    public String getName() {
        return "start";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length != 1) {
            sender.sendMessage(ChatColor.RED + "コマンドの引数が間違ってます。");
            return true;
        }

        if (PanelCraft.game == null || PanelCraft.game.isFinished()) {
            sender.sendMessage(ChatColor.RED + "セットアップが完了していません。");
            return true;
        }

        if (PanelCraft.game.isRunning()) {
            sender.sendMessage(ChatColor.RED + "現在実行中です。");
            return true;
        }

        switch (args[0]) {
            case "random":
                PanelCraft.game.run(Game.GameMode.Random);
                break;
            case "stepon":
                PanelCraft.game.run(Game.GameMode.StepOn);
                break;
            default:
                sender.sendMessage(ChatColor.RED + "そのようなゲームモードは存在しません。");
                break;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(String[] args) {
        switch (args.length) {
            case 1:
                return Arrays.asList("random", "stepon");
            default:
                return Collections.emptyList();
        }
    }

}
