package net.kunmc.lab.panelcraft;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PanelCommand implements CommandExecutor, TabCompleter {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("panelcraft.panel")) {
            return true;
        }

        if (args.length < 1) {
            return true;
        }

        switch (args[0].toLowerCase()) {
            case "start":
                return onStart(sender, Arrays.copyOfRange(args, 1, args.length));
            case "stop":
                return onStop(sender, Arrays.copyOfRange(args, 1, args.length));
        }

        return true;
    }

    private boolean onStart(CommandSender sender, String[] args) {
        if (PanelCraft.game != null) {
            sender.sendMessage("既に始まってるよ～ん");
            return true;
        }
        
        if (args.length < 1) {
            sender.sendMessage("引数が足りないよ～ん");
            return true;
        }

        if (!(sender instanceof Player)) {
            return true;
        }
        Player player = (Player) sender;
        Location location = player.getLocation();

        switch (args[0].toLowerCase()) {
            case "random":
                PanelCraft.game = new RandomGame(location.getBlockX(), location.getBlockY(), location.getBlockZ());
                break;
            case "stepon":
                PanelCraft.game = new StepOnGame(location.getBlockX(), location.getBlockY(), location.getBlockZ());
                break;
            default:
                sender.sendMessage("引数の形式が間違ってるよ～ん");
                return true;
        }
        PanelCraft.game.runTaskTimer(PanelCraft.instance, 1, 0);

        return true;
    }

    private boolean onStop(CommandSender sender, String[] args) {
        if (PanelCraft.game == null) {
            sender.sendMessage("既に終わってるよ～ん");
            return true;
        }

        PanelCraft.game.stop();

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> result;

        switch (args.length) {
            case 1:
                result = Arrays.asList("start", "stop");
                break;
            case 2:
                switch (args[0].toLowerCase()) {
                    case "start":
                        result = Arrays.asList("random", "stepon");
                        break;
                    default:
                        return Collections.emptyList();
                }
                break;
            default:
                return Collections.emptyList();
        }

        String last = args[args.length - 1].toLowerCase();
        return result.stream()
                .filter(opt -> opt.startsWith(last))
                .collect(Collectors.toList());
    }

}
