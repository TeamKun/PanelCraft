package net.kunmc.lab.panelcraft;

import net.kunmc.lab.panelcraft.commands.CommandMain;
import org.bukkit.plugin.java.JavaPlugin;

public final class PanelCraft extends JavaPlugin {
    public static PanelCraft instance;
    public static Game game;

    @Override
    public void onEnable() {
        instance = this;

        CommandMain command = new CommandMain();
        getServer().getPluginCommand("panel").setExecutor(command);
        getServer().getPluginCommand("panel").setTabCompleter(command);
    }

    @Override
    public void onDisable() {

    }

}
