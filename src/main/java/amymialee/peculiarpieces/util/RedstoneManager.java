package amymialee.peculiarpieces.util;

import net.minecraft.block.Blocks;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class RedstoneManager {
    private static final LinkedHashMap<World, LinkedHashMap<BlockPos, RedstoneInstance>> redstoneInstances = new LinkedHashMap<>();

    //TODO: Save and Load to the worlds, so that stopping the server doesn't leave lingering redstone.

    public static void tick() {
        var expirations = new ArrayList<Pair<World, BlockPos>>();
        redstoneInstances.forEach((w, m) -> m.forEach((p, r) -> {
            if (r.tick()) {
                expirations.add(new Pair<>(w, p));
            }
        }));
        for (var pair : expirations) {
            redstoneInstances.get(pair.getLeft()).remove(pair.getRight());
            updatePos(pair.getLeft(), pair.getRight());
        }
    }

    public static void addInstance(World world, BlockPos pos, RedstoneInstance instance) {
        if (!redstoneInstances.containsKey(world)) {
            redstoneInstances.put(world, new LinkedHashMap<>());
        }
        var map = redstoneInstances.get(world);
        map.remove(pos);
        map.put(pos, instance);
        updatePos(world, pos);
    }

    private static void updatePos(World world, BlockPos pos) {
        world.getBlockState(pos).neighborUpdate(world, pos, Blocks.REDSTONE_BLOCK, pos, true);
        world.updateNeighbors(pos, Blocks.REDSTONE_BLOCK);
    }

    public static RedstoneInstance getInstance(World world, BlockPos pos) {
        var map = redstoneInstances.get(world);
        if (map != null) {
            return map.get(pos);
        }
        return null;
    }

    public static boolean isPowered(World world, BlockPos pos) {
        var map = redstoneInstances.get(world);
        if (map != null) {
            var instance = map.get(pos);
            return instance != null && instance.getLifetime() > 0;
        }
        return false;
    }

    public static boolean isStrong(World world, BlockPos pos) {
        var map = redstoneInstances.get(world);
        if (map != null) {
            var instance = map.get(pos);
            if (instance != null && instance.getLifetime() > 0) {
                return instance.isStrong();
            }
        }
        return false;
    }

    public static int getPower(World world, BlockPos pos) {
        var map = redstoneInstances.get(world);
        if (map != null) {
            var instance = map.get(pos);
            if (instance != null && instance.getLifetime() > 0) {
                return instance.getPower();
            }
        }
        return 0;
    }
}