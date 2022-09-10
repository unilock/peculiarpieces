package amymialee.peculiarpieces.util;

import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;

public interface ExtraPlayerDataWrapper {
    int getGameModeDuration();
    void setGameModeDuration(int duration);
    GameMode getStoredGameMode();
    void setStoredGameMode(GameMode gameMode);
    Vec3d getCheckpointPos();
    void setCheckpointPos(Vec3d vec3d);
    RegistryKey<World> getCheckpointWorld();
    void setCheckpointWorld(RegistryKey<World> world);
    double getBouncePower();
    void setBouncePower(double bouncePower);
}