package net.kunmc.lab.panelcraft;

import org.bukkit.Bukkit;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class RandomGame extends Game {
    private final Random random;

    public RandomGame(int x, int y, int z) {
        super(x, y, z);
        random = new Random(System.currentTimeMillis());
    }

    @Override
    public void run() {
        List<Panel> alivePanel = panel.stream()
                .filter(Panel::isAlive)
                .filter(panel -> !panel.isFalling())
                .collect(Collectors.toList());

        if (alivePanel.size() == 0) {
            cancel();
            Bukkit.broadcastMessage("ゲームが終了しました。");
            PanelCraft.game = null;
            return;
        }

        int threshold; // 閾値
        int total = Game.range * Game.range;

        if (alivePanel.size() < total * 0.1) {
            threshold = 5;
        } else if (alivePanel.size() < total * 0.25) {
            threshold = 20;
        } else {
            threshold = 100;
        }

        if (random.nextInt(100) < threshold) {
            int index = random.nextInt(alivePanel.size());

            alivePanel.get(index).fall();
        }
    }

    @Override
    public PanelMode getMode() {
        return PanelMode.Random;
    }

}
