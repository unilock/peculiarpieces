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
        ArrayList<Pair<World, BlockPos>> expirations = new ArrayList<>();
        redstoneInstances.forEach((w, m) -> m.forEach((p, r) -> {
            if (r.tick()) {
                expirations.add(new Pair<>(w, p));
            }
        }));
        for (Pair<World, BlockPos> pair : expirations) {
            redstoneInstances.get(pair.getLeft()).remove(pair.getRight());
            updatePos(pair.getLeft(), pair.getRight());
        }
    }

    public static void addInstance(World world, BlockPos pos, RedstoneInstance instance) {
        if (!redstoneInstances.containsKey(world)) {
            redstoneInstances.put(world, new LinkedHashMap<>());
        }
        LinkedHashMap<BlockPos, RedstoneInstance> map = redstoneInstances.get(world);
        map.remove(pos);
        map.put(pos, instance);
        updatePos(world, pos);
    }

    private static void updatePos(World world, BlockPos pos) {
        world.getBlockState(pos).neighborUpdate(world, pos, Blocks.REDSTONE_BLOCK, pos, true);
        world.updateNeighbors(pos, Blocks.REDSTONE_BLOCK);
    }

    public static RedstoneInstance getInstance(World world, BlockPos pos) {
        return redstoneInstances.get(world).get(pos);
    }

    public static boolean isPowered(World world, BlockPos pos) {
        RedstoneInstance instance = redstoneInstances.get(world).get(pos);
        return instance != null && instance.getLifetime() > 0;
    }

    public static boolean isStrong(World world, BlockPos pos) {
        RedstoneInstance instance = redstoneInstances.get(world).get(pos);
        if (instance != null && instance.getLifetime() > 0) {
            return instance.isStrong();
        }
        return false;
    }

    public static int getPower(World world, BlockPos pos) {
        RedstoneInstance instance = redstoneInstances.get(world).get(pos);
        if (instance != null && instance.getLifetime() > 0) {
            return instance.getPower();
        }
        return 0;
    }
}