package net.kunmc.lab.panelcraft;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;

public abstract class Game extends BukkitRunnable {
    protected static int range;
    protected static LinkedList<Panel> panel = null;

    public static boolean isSetupCompleted() {
        return panel != null;
    }

    public static void setup(int x, int y, int z, int range) {
        Game.range = range;
        panel = new LinkedList<>();
        for (int i = 0; i < range; i++) {
            for (int j = 0; j < range; j++) {
                Material material;

                if (i % 2 == j % 2) {
                    material = Material.GREEN_WOOL;
                } else {
                    material = Material.BLACK_WOOL;
                }
                panel.add(new Panel(x + j * Panel.range - Panel.range * range / 2, y - 1, z + i * Panel.range - Panel.range * range / 2, material));
            }
        }
    }

    public void stop() {
        panel.forEach(Panel::fall);
        panel = null;
        cancel();
        PanelCraft.game = null;
        Bukkit.broadcastMessage(ChatColor.GREEN + "ゲームが終了しました。");
    }

    public abstract PanelMode getMode();

    public enum PanelMode {
        Random,
        StepOn,
    }

}
