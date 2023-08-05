package amymialee.peculiarpieces.util;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;

import java.util.ArrayList;

public class WarpManager {
    private static final ArrayList<WarpInstance> dueTeleports = new ArrayList<>();

    public static void tick() {
        for (int i = 0; i < dueTeleports.size();) {
            WarpInstance instance = dueTeleports.get(i);
            Entity entity = instance.getEntity();
            MinecraftServer server = entity.getServer();
            if (server != null) {
                ServerWorld targetWorld = instance.hasWorld() ? server.getWorld(instance.getWorld()) : server.getWorld(instance.getEntity().getWorld().getRegistryKey());
                Vec3d targetPos = instance.hasPosition() ? instance.getPosition() : entity.getPos();
                Vec3d targetVelocity = instance.hasVelocity() ? instance.getVelocity() : entity.getVelocity();
                float targetYaw = instance.hasYaw() ? instance.getYaw() : entity.getYaw();
                float targetPitch = instance.hasPitch() ? instance.getPitch() : entity.getPitch();
                if (targetWorld == null) {
                    targetWorld = server.getWorld(instance.getEntity().getWorld().getRegistryKey());
                }
                if (instance.hasParticles()) {
                    entity.getWorld().sendEntityStatus(entity, (byte)46);
                }
                FabricDimensions.teleport(entity, targetWorld, new TeleportTarget(targetPos, targetVelocity, targetYaw, targetPitch));
                if (instance.hasParticles()) {
                    entity.getWorld().sendEntityStatus(entity, (byte)46);
                }
                if (entity instanceof PathAwareEntity) {
                    ((PathAwareEntity)entity).getNavigation().stop();
                }
            }
            dueTeleports.remove(instance);
        }
    }

    public static void queueTeleport(WarpInstance instance) {
        dueTeleports.add(instance);
    }
}