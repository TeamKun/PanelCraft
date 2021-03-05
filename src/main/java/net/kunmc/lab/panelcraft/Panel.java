package net.kunmc.lab.panelcraft;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.scheduler.BukkitRunnable;

public class Panel {
    public static final int range = 3;
    public static final long vibrationTime = 3000; // 振動時間（ms）
    public static final long vibrationPeriod = 500; // 振動周期（ms）

    private final int x;
    private final int y;
    private final int z;
    private final Material material;
    private boolean alive = true;
    private boolean falling = false;

    public Panel(int x, int y, int z, Material material) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.material = material;
        setBlock(material);
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isFalling() {
        return falling;
    }

    public void fall() {
        if (falling || !alive) {
            return;
        }

        falling = true;

        new BukkitRunnable() {
            private final long startTime = System.currentTimeMillis();

            @Override
            public void run() {
                // 経過時間（ms）
                long elapsedTime = System.currentTimeMillis() - startTime;

                if (elapsedTime < vibrationTime) {
                    if (elapsedTime % vibrationPeriod < vibrationPeriod / 2) {
                        setBlock(Material.RED_WOOL);
                    } else {
                        setBlock(material);
                    }
                } else {
                    for (int i = 0; i < range; i++) {
                        for (int j = 0; j < range; j++) {
                            World world = Bukkit.getWorld("world");
                            world.spawnFallingBlock(new Location(world, x + j, y, z + i), material.createBlockData())
                                    .setDropItem(false);
                        }
                    }
                    setBlock(Material.AIR);
                    falling = false;
                    alive = false;
                    this.cancel();
                }
            }

        }.runTaskTimer(PanelCraft.instance, 0, 1);
    }

    public boolean checkCollision(int x, int y, int z) {
        if (x < this.x || this.x + range < x) {
            return false;
        }
        if (z < this.z || this.z + range < z) {
            return false;
        }

        return y == this.y;
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
