package net.kunmc.lab.panelcraft;

import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;


public abstract class Game extends BukkitRunnable {
    public static final int range = 27;

    protected LinkedList<Panel> panel;

    public Game(int x, int y, int z) {
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
    }

    public abstract PanelMode getMode();

    public enum PanelMode {
        Random,
        StepOn,
    }

}
