package net.kunmc.lab.panelcraft;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Game {
    public static final int defaultRange = 27;

    private final List<Panel> panel = new LinkedList<>();
    private BukkitRunnable game;

    public Game(Location location, int range, int panelX, int panelZ, int marginX, int marginZ) {
        this(location.getBlockX(), location.getBlockY(), location.getBlockZ(), range, panelX, panelZ, marginX, marginZ);
    }

    public Game(int x, int y, int z, int range, int panelX, int panelZ, int marginX, int marginZ) {
        for (int i = 0; i < range; i++) {
            for (int j = 0; j < range; j++) {
                Material material;

                if (i % 2 == j % 2) {
                    material = Material.GREEN_WOOL;
                } else {
                    material = Material.BLACK_WOOL;
                }
                panel.add(new Panel(
                        x + (panelX + marginX) * (j - range / 2),
                        y - 1,
                        z + (panelZ + marginZ) * (i - range / 2),
                        panelX,
                        panelZ,
                        material));
            }
        }
    }

    public void run(GameMode mode) {
        switch (mode) {
            case Random:
                game = new RandomGame();
                break;
            case StepOn:
                game = new StepOnGame();
                break;
            default:
                return;
        }

        game.runTaskTimer(PanelCraft.instance, 0, 1);
    }

    public void stop() {
        panel.forEach(Panel::fall);
        if (game != null) {
            game.cancel();
        }
    }

    public void erase() {
        panel.forEach(Panel::erase);
        if (game != null) {
            game.cancel();
        }
    }

    public boolean isRunning() {
        return game != null && !game.isCancelled();
    }

    public boolean isFinished() {
        return game != null && game.isCancelled();
    }

    public enum GameMode {
        Random,
        StepOn,
    }

    private class RandomGame extends BukkitRunnable {
        private final Random random = new Random(System.currentTimeMillis());

        @Override
        public void run() {
            List<Panel> alivePanel = panel.stream()
                    .parallel()
                    .filter(Panel::isAlive)
                    .filter(panel -> !panel.isFalling())
                    .collect(Collectors.toList());

            if (alivePanel.size() == 0) {
                cancel();
                return;
            }

            // 閾値
            int threshold = alivePanel.size() / 5 + 5;

            if (random.nextInt(100) < threshold) {
                int index = random.nextInt(alivePanel.size());

                alivePanel.get(index).fall();
            }
        }
    }

    private class StepOnGame extends BukkitRunnable implements Listener {

        public StepOnGame() {
            PanelCraft.instance.getServer().getPluginManager().registerEvents(this, PanelCraft.instance);
        }

        @Override
        public void run() {
            if (panel.stream().noneMatch(panel -> panel.isAlive() && !panel.isFalling())) {
                cancel();
            }
        }

        @Override
        public synchronized void cancel() throws IllegalStateException {
            super.cancel();
            PlayerMoveEvent.getHandlerList().unregister(this);
        }

        @EventHandler
        public void onMove(PlayerMoveEvent event) {
            if (!event.getPlayer().isOnGround()) {
                return;
            }

            panel.stream()
                    .parallel()
                    .filter(Panel::isAlive)
                    .filter(panel -> !panel.isFalling())
                    .filter(panel -> panel.checkCollision(event.getPlayer().getLocation().getBlockX(), event.getPlayer().getLocation().getBlockY() - 1, event.getPlayer().getLocation().getBlockZ()))
                    .forEach(Panel::fall);
        }
    }

}
