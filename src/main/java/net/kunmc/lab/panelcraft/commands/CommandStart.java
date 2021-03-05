package net.kunmc.lab.panelcraft.commands;

import net.kunmc.lab.panelcraft.PanelCraft;
import net.kunmc.lab.panelcraft.RandomGame;
import org.bukkit.command.CommandSender;

import java.util.Arrays;
import java.util.List;

public class CommandStart implements CommandBase {

    @Override
    public String getName() {
        return "start";
    }

    @Override
    public boolean onCommand(CommandSender sender, String[] args) {
        if (args.length != 1) {
            return true;
        }

        switch (args[0]) {
            case "random":
                break;
            case "stepon":
                break;
        }

        return true;
    }

    @Override
    public List<String> onTabComplete(String[] args) {
        if (args.length == 1) {
            return Arrays.asList("random", "stepon");
        }

        return null;
    }

}
