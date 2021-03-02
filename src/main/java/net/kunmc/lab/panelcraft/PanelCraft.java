package net.kunmc.lab.panelcraft;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class PanelCraft extends JavaPlugin {
    public static PanelCraft instance;
    public static BukkitRunnable game;

    @Override
    public void onEnable() {
        instance = this;
    }

    @Override
    public void onDisable() {

    }

}
