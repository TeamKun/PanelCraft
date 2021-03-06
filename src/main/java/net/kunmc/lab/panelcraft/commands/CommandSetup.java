package net.kunmc.lab.panelcraft.commands;

import net.kunmc.lab.panelcraft.Game;
import net.kunmc.lab.panelcraft.Panel;
import net.kunmc.lab.panelcraft.PanelCraft;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;
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

        Location location = ((Player) sender).getLocation();
        int range = Game.defaultRange;
        int panelX = Panel.defaultWidth;
        int panelZ = Panel.defaultWidth;
        int marginX = 0;
        int marginZ = 0;
        switch (args.length) {
            case 0:
                break;
            case 1:
                try {
                    range = Integer.parseInt(args[0]);
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "引数の形式が間違っているようです。");
                    return true;
                }
                break;
            case 3:
                try {
                    String[] arg1 = args[1].split(":");
                    String[] arg2 = args[2].split(":");

                    switch (arg1.length) {
                        case 2:
                            marginX = Integer.parseInt(arg1[1]);
                        case 1:
                            panelX = Integer.parseInt(arg1[0]);
                            switch (arg2.length) {
                                case 2:
                                    marginZ = Integer.parseInt(arg2[1]);
                                case 1:
                                    panelZ = Integer.parseInt(arg2[0]);
                                    break;
                                default:
                                    sender.sendMessage(ChatColor.RED + "引数の形式が間違っているようです。");
                                    return true;
                            }
                            break;
                        default:
                            sender.sendMessage(ChatColor.RED + "引数の形式が間違っているようです。");
                            return true;
                    }
                } catch (NumberFormatException e) {
                    sender.sendMessage(ChatColor.RED + "引数の形式が間違っているようです。");
                    return true;
                }
                break;
            default:
                sender.sendMessage(ChatColor.RED + "コマンドの引数が間違ってます。");
                return true;
        }
        PanelCraft.game = new Game(location, range, panelX, panelZ, marginX, marginZ);

        return true;
    }

    @Override
    public List<String> onTabComplete(String[] args) {
        switch (args.length) {
            case 1:
                return Collections.singletonList("<range>");
            case 2:
            case 3:
                return Arrays.asList("<width>", "<width:margin>");
            default:
                return Collections.emptyList();
        }
    }

}
