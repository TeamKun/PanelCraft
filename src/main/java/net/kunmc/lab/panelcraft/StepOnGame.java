package net.kunmc.lab.panelcraft;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class StepOnGame extends Game implements Listener {

    public StepOnGame(int x, int y, int z) {
        PanelCraft.instance.getServer().getPluginManager().registerEvents(this, PanelCraft.instance);
    }

    @Override
    public void run() {
        if (panel.stream().noneMatch(panel -> panel.isAlive() && !panel.isFalling())) {
            stop();
        }
    }

    @Override
    public PanelMode getMode() {
        return PanelMode.StepOn;
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
