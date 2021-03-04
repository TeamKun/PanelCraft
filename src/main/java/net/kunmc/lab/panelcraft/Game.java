package net.kunmc.lab.panelcraft;

import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;


public abstract class Game extends BukkitRunnable {
    public static final int range = 9;

    protected LinkedList<Panel> panel;

    public Game(int x, int y, int z) {
        panel = new LinkedList<>();
        for (int i = 0; i < range; i++) {
            for (int j = 0; j < range; j++) {
                panel.add(new Panel(x + j * Panel.range - Panel.range * range / 2, y - 1, z + i * Panel.range - Panel.range * range / 2));
            }
        }
    }

    public void stop() {
        panel.forEach(Panel::fall);
    }

    public abstract PanelMode getMode();

    public enum PanelMode {
        Random,
        StepOn,
    }

}
