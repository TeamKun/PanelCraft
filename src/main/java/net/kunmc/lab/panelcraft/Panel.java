package net.kunmc.lab.panelcraft;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftEntity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class Panel {
    public static final int range = 3;
    public static final long vibrationTime = 3000; // 振動時間（ms）
    public static final long vibrationPeriod = 200; // 振動周期（ms）

    private final int x;
    private final int y;
    private final int z;
    private boolean alive = true;
    private boolean falling = false;
    private final List<FallingBlock> fallingBlocks = new ArrayList<>();

    public Panel(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        setBlock(Material.IRON_BLOCK);
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isFalling() {
        return falling;
    }

    public void fall() {
        falling = true;
        for (int i = 0; i < range; i++) {
            for (int j = 0; j < range; j++) {
                Location location = new Location(Bukkit.getWorld("world"), x + j, y, z + i);
                FallingBlock block = Bukkit.getWorld("world").spawnFallingBlock(location, Material.RED_WOOL.createBlockData());
                block.setGravity(false);
                block.setDropItem(false);
                block.setGlowing(true);
                ((CraftEntity) block).getHandle().noclip = true;
                ((CraftEntity) block).getHandle().ticksLived = 1;
                fallingBlocks.add(block);
            }
        }
        setBlock(Material.BARRIER);

        new BukkitRunnable() {
            private final long startTime = System.currentTimeMillis();

            @Override
            public void run() {
                // 経過時間（ms）
                long elapsedTime = System.currentTimeMillis() - startTime;

                if (elapsedTime < vibrationTime) {
                    Vector velocity;

                    if (elapsedTime % vibrationPeriod < vibrationPeriod / 2) {
                        velocity = new Vector(0.2, 0, 0);
                    } else {
                        velocity = new Vector(-0.2, 0, 0);
                    }
                    fallingBlocks.forEach(block -> block.setVelocity(velocity));
                } else {
                    fallingBlocks.forEach(block -> {
                        block.setGravity(true);
                        ((CraftEntity) block).getHandle().noclip = false;
                    });
                    setBlock(Material.AIR);
                    falling = false;
                    alive = false;
                    this.cancel();
                }
            }

        }.runTaskTimer(PanelCraft.instance, 0, 5);
    }

    private void setBlock(Material material) {
        for (int i = 0; i < range; i++) {
            for (int j = 0; j < range; j++) {
                Location location = new Location(Bukkit.getWorld("world"), x + j, y, z + i);
                location.getBlock().setType(material);
            }
        }
    }

}
