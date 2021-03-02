package net.kunmc.lab.panelcraft;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Arrays;
import java.util.List;

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

        switch (args[0].toLowerCase()) {
            case "random":
                PanelCraft.game = new RandomGame();
                PanelCraft.game.runTaskTimer(PanelCraft.instance, 1, 0);
                return true;
            case "stepon":
                PanelCraft.game = new StepOnGame();
                PanelCraft.game.runTaskTimer(PanelCraft.instance, 1, 0);
                return true;
        }

        return true;
    }

    private boolean onStop(CommandSender sender, String[] args) {
        if (PanelCraft.game == null) {
            sender.sendMessage("既に終わってるよ～ん");
            return true;
        }

        PanelCraft.game.cancel();
        PanelCraft.game = null;

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }

}
