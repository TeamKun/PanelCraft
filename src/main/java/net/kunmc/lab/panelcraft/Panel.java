package net.kunmc.lab.panelcraft;

import org.bukkit.*;
import org.bukkit.scheduler.BukkitRunnable;

public class Panel {
    public static final int defaultWidth = 3;
    public static final long blinkTime = 3000; // 振動時間（ms）
    public static final long blinkPeriod = 500; // 振動周期（ms）

    private final int x;
    private final int y;
    private final int z;
    private final int widthX;
    private final int widthZ;
    private final Material material;
    private boolean alive = true;
    private boolean falling = false;
    private Material currentMaterial;

    public Panel(int x, int y, int z, int widthX, int widthZ, Material material) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.widthX = widthX;
        this.widthZ = widthZ;
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

                World world = Bukkit.getWorld("world");

                if (world == null) {
                    System.err.println("[ERROR]: ワールドが取得できません！");
                    return;
                }

                if (elapsedTime < blinkTime) {
                    if (elapsedTime % blinkPeriod < blinkPeriod / 2) {
                        if (currentMaterial != Material.RED_WOOL) {
                            setBlock(Material.RED_WOOL);
                        }
                    } else {
                        if (currentMaterial != material) {
                            setBlock(material);
                        }
                    }
                } else {
                    for (int i = 0; i < widthZ; i++) {
                        for (int j = 0; j < widthX; j++) {
                            world.spawnFallingBlock(new Location(world, x + j + 0.5, y, z + i + 0.5), material.createBlockData())
                                    .setDropItem(false);
                        }
                    }
                    world.playSound(new Location(world, x + (widthX * 0.5), y, z + (widthZ * 0.5)),
                            Sound.BLOCK_BAMBOO_BREAK, 1.0f, 1.0f);
                    setBlock(Material.AIR);
                    falling = false;
                    alive = false;
                    this.cancel();
                }
            }

        }.runTaskTimer(PanelCraft.instance, 0, 1);
    }

    public void erase() {
        setBlock(Material.AIR);
        falling = false;
        alive = false;
    }

    public boolean checkCollision(Location location) {
        return checkCollision(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public boolean checkCollision(int x, int y, int z) {
        if (x < this.x || this.x + widthX - 1 < x) {
            return false;
        }
        if (z < this.z || this.z + widthZ - 1 < z) {
            return false;
        }

        return y == this.y;
    }

    public void switchMaterial() {
        if (!isAlive() || isFalling()) {
            return;
        }

        if (currentMaterial == material) {
            setBlock(Material.AIR);
        } else if (currentMaterial == Material.AIR) {
            setBlock(material);
        }
    }

    private void setBlock(Material material) {
        currentMaterial = material;
        for (int i = 0; i < widthZ; i++) {
            for (int j = 0; j < widthX; j++) {
                Location location = new Location(Bukkit.getWorld("world"), x + j, y, z + i);
                location.getBlock().setType(material);
            }
        }
    }

}
