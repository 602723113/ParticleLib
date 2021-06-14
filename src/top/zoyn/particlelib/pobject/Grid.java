package top.zoyn.particlelib.pobject;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class Grid extends ParticleObject {

    private final double gridLength;
    private Location minimumLocation;
    private Location maximumLocation;
    private boolean isXDimension = false;
    private boolean isYDimension = true;

    public Grid(Location minimumLocation, Location maximumLocation) {
        this(minimumLocation, maximumLocation, 1.2D);
    }

    public Grid(Location minimumLocation, Location maximumLocation, double gridLength) {
        this.minimumLocation = minimumLocation;
        this.maximumLocation = maximumLocation;
        // 平面检查
        if (minimumLocation.getBlockX() != maximumLocation.getBlockX()) {
            if (minimumLocation.getBlockZ() != maximumLocation.getBlockZ()) {
                if (minimumLocation.getBlockY() != maximumLocation.getBlockY()) {
                    throw new IllegalArgumentException("请将两点设定在X平面, Y平面或Z平面上(即一个方块的面上)");
                }
            }
        }
        if (minimumLocation.getBlockX() == maximumLocation.getBlockX()) {
            isXDimension = false;
        }
        if (minimumLocation.getBlockY() == maximumLocation.getBlockY()) {
            isYDimension = true;
        }
        if (minimumLocation.getBlockZ() == maximumLocation.getBlockZ()) {
            isXDimension = true;
        }

        this.gridLength = gridLength;
    }

    @Override
    public void show() {
        double height = Math.abs(maximumLocation.getY() - minimumLocation.getY());
        double weight;
        if (isXDimension) {
            weight = Math.abs(maximumLocation.getX() - minimumLocation.getX());
        } else {
            weight = Math.abs(maximumLocation.getZ() - minimumLocation.getZ());
        }
        int heightSideLine = (int) (height / gridLength);
        int weightSideLine = (int) (weight / gridLength);

        // 为防止给定的最小和最高点出现反向的情况, 这里做了个查找操作
        Location minLocation = findMinimumLocation();
        Location maxLocation = findMaximumLocation();

        for (int i = 1; i <= heightSideLine; i++) {
            Vector vector = maxLocation.clone().subtract(minLocation).toVector();
            vector.setY(0).normalize();

            Location start = minLocation.clone().add(0, i * gridLength, 0);
            for (double j = 0; j < weight; j += 0.2) {
                spawnParticle(start.clone().add(vector.clone().multiply(j)));
            }
        }

        for (int i = 1; i <= weightSideLine; i++) {
            Vector vector = maxLocation.clone().subtract(minLocation).toVector();
            Location start;
            if (isXDimension) {
                vector.setX(0).normalize();
                start = minLocation.clone().add(i * gridLength, 0, 0);
            } else {
                vector.setZ(0).normalize();
                start = minLocation.clone().add(0, 0, i * gridLength);
            }

            for (double j = 0; j < height; j += 0.2) {
                spawnParticle(start.clone().add(vector.clone().multiply(j)));
            }
        }
    }

    private Location findMinimumLocation() {
        if (isXDimension) {
            // 以下的代码请把 minimumLoc 当做平面坐标系的中心
            // maximumLoc 在第一第二象限的情况
            if (minimumLocation.getBlockY() < maximumLocation.getBlockY()) {
                if (minimumLocation.getBlockX() > maximumLocation.getBlockX()) {
                    Location maxToLower = maximumLocation.clone();
                    maxToLower.setY(minimumLocation.getY());
                    return maxToLower;
                }
            // maximumLoc 在第三第四象限的情况
            } else {
                if (minimumLocation.getBlockX() > maximumLocation.getBlockX()) {
                    return maximumLocation;
                } else {
                    Location minToLower = minimumLocation.clone();
                    minToLower.setY(maximumLocation.getY());
                    return minToLower;
                }
            }
        } else {
            if (minimumLocation.getBlockY() < maximumLocation.getBlockY()) {
                if (minimumLocation.getBlockZ() > maximumLocation.getBlockZ()) {
                    Location maxToLower = maximumLocation.clone();
                    maxToLower.setY(minimumLocation.getY());
                    return maxToLower;
                }
            } else {
                if (minimumLocation.getBlockZ() > maximumLocation.getBlockZ()) {
                    return maximumLocation;
                } else {
                    Location minToLower = minimumLocation.clone();
                    minToLower.setY(maximumLocation.getY());
                    return minToLower;
                }
            }
        }
        return minimumLocation;
    }

    private Location findMaximumLocation() {
        if (isXDimension) {
            if (minimumLocation.getBlockY() < maximumLocation.getBlockY()) {
                if (minimumLocation.getBlockX() > maximumLocation.getBlockX()) {
                    Location minToHigher = minimumLocation.clone();
                    minToHigher.setY(maximumLocation.getY());
                    return minToHigher;
                }
            } else {
                if (minimumLocation.getBlockX() > maximumLocation.getBlockX()) {
                    return minimumLocation;
                } else {
                    Location maxToHigher = maximumLocation.clone();
                    maxToHigher.setY(minimumLocation.getY());
                    return maxToHigher;
                }
            }
        } else {
            if (minimumLocation.getBlockY() < maximumLocation.getBlockY()) {
                if (minimumLocation.getBlockZ() > maximumLocation.getBlockZ()) {
                    Location minToHigher = minimumLocation.clone();
                    minToHigher.setY(maximumLocation.getY());
                    return minToHigher;
                }
            } else {
                if (minimumLocation.getBlockZ() > maximumLocation.getBlockZ()) {
                    return minimumLocation;
                } else {
                    Location maxToHigher = maximumLocation.clone();
                    maxToHigher.setY(minimumLocation.getY());
                    return maxToHigher;
                }
            }
        }
        return maximumLocation;
    }

    public Location getMinimumLocation() {
        return minimumLocation;
    }

    public void setMinimumLocation(Location minimumLocation) {
        this.minimumLocation = minimumLocation;
    }

    public Location getMaximumLocation() {
        return maximumLocation;
    }

    public void setMaximumLocation(Location maximumLocation) {
        this.maximumLocation = maximumLocation;
    }
}
