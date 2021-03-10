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
        if (args.length == 0) {
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
                PanelCraft.game.run(PanelCraft.game.new RandomGame());
                break;
            case "stepon":
                PanelCraft.game.run(PanelCraft.game.new StepOnGame());
                break;
            case "switch":
                if (args.length == 1) {
                    PanelCraft.game.run(PanelCraft.game.new SwitchGame(1000));
                } else {
                    try {
                        PanelCraft.game.run(PanelCraft.game.new SwitchGame(Integer.parseInt(args[1])));
                    } catch (NumberFormatException e) {
                        sender.sendMessage(ChatColor.RED + "引数の形式が間違っているようです。");
                        return true;
                    }
                }
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
                return Arrays.asList("random", "stepon", "switch");
            case 2:
                return args[0].equalsIgnoreCase("switch") ? Collections.singletonList("<period>") : Collections.emptyList();
            default:
                return Collections.emptyList();
        }
    }

}
