package amymialee.peculiarpieces.util;

import net.minecraft.entity.Entity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

@SuppressWarnings({"UnusedReturnValue", "unused"})
public class WarpInstance {
    @Nonnull
    private final Entity entity;
    private boolean hasWorld = false;
    private RegistryKey<World> world;
    private boolean hasPosition = false;
    private boolean hasXPos = false;
    private double xPos = 0;
    private boolean hasYPos = false;
    private double yPos = 0;
    private boolean hasZPos = false;
    private double zPos = 0;
    private boolean hasVelocity = false;
    private boolean hasXVel = false;
    private double xVel = 0;
    private boolean hasYVel = false;
    private double yVel = 0;
    private boolean hasZVel = false;
    private double zVel = 0;
    private boolean hasPitch = false;
    private float pitch = 0;
    private boolean hasYaw = false;
    private float yaw = 0;
    private boolean particles = false;

    public WarpInstance(@NotNull Entity entity) {
        this.entity = entity;
    }

    public static WarpInstance of(Entity entity) {
        return new WarpInstance(entity);
    }

    public WarpInstance world(RegistryKey<World> world) {
        this.hasWorld = true;
        this.world = world;
        return this;
    }

    public WarpInstance position(Vec3d position) {
        this.hasPosition = true;
        this.x(position.getX());
        this.y(position.getY());
        this.z(position.getZ());
        return this;
    }

    public WarpInstance position(BlockPos position) {
        return position(Vec3d.ofBottomCenter(position));
    }

    public WarpInstance x(double x) {
        this.hasPosition = true;
        this.hasXPos = true;
        this.xPos = x;
        return this;
    }

    public WarpInstance y(double y) {
        this.hasPosition = true;
        this.hasYPos = true;
        this.yPos = y;
        return this;
    }

    public WarpInstance z(double z) {
        this.hasPosition = true;
        this.hasZPos = true;
        this.zPos = z;
        return this;
    }

    public WarpInstance velocity(Vec3d velocity) {
        this.hasVelocity = true;
        this.xVel(velocity.getX());
        this.yVel(velocity.getY());
        this.zVel(velocity.getZ());
        return this;
    }

    public WarpInstance xVel(double x) {
        this.hasVelocity = true;
        this.hasXVel = true;
        this.xVel = x;
        return this;
    }

    public WarpInstance yVel(double y) {
        this.hasVelocity = true;
        this.hasYVel = true;
        this.yVel = y;
        return this;
    }

    public WarpInstance zVel(double z) {
        this.hasVelocity = true;
        this.hasZVel = true;
        this.zVel = z;
        return this;
    }

    public WarpInstance pitch(float pitch) {
        this.hasPitch = true;
        this.pitch = pitch;
        return this;
    }

    public WarpInstance yaw(float yaw) {
        this.hasYaw = true;
        this.yaw = yaw;
        return this;
    }

    public WarpInstance particles() {
        this.particles = true;
        return this;
    }

    public Vec3d getPosition() {
        return new Vec3d(this.hasXPos ? this.xPos : this.entity.getX(), this.hasYPos ? this.yPos : this.entity.getY(), this.hasZPos ? this.zPos : this.entity.getZ());
    }

    public Vec3d getVelocity() {
        Vec3d entity = this.entity.getVelocity();
        return new Vec3d(this.hasXVel ? this.xVel : entity.getX(), this.hasYVel ? this.yVel : entity.getY(), this.hasZVel ? this.zVel : entity.getZ());
    }

    @Nonnull
    public Entity getEntity() {
        return this.entity;
    }

    public boolean hasWorld() {
        return this.hasWorld;
    }

    public RegistryKey<World> getWorld() {
        return this.world;
    }

    public boolean hasPosition() {
        return this.hasPosition;
    }

    public boolean hasXPos() {
        return this.hasXPos;
    }

    public double getXPos() {
        return this.xPos;
    }

    public boolean hasYPos() {
        return this.hasYPos;
    }

    public double getYPos() {
        return this.yPos;
    }

    public boolean hasZPos() {
        return this.hasZPos;
    }

    public double getzPos() {
        return this.zPos;
    }

    public boolean hasVelocity() {
        return this.hasVelocity;
    }

    public boolean hasXVel() {
        return this.hasXVel;
    }

    public double getXVel() {
        return this.xVel;
    }

    public boolean hasYVel() {
        return this.hasYVel;
    }

    public double getYVel() {
        return this.yVel;
    }

    public boolean hasZVel() {
        return this.hasZVel;
    }

    public double getzVel() {
        return this.zVel;
    }

    public boolean hasPitch() {
        return this.hasPitch;
    }

    public float getPitch() {
        return this.pitch;
    }

    public boolean hasYaw() {
        return this.hasYaw;
    }

    public float getYaw() {
        return this.yaw;
    }

    public boolean hasParticles() {
        return this.particles;
    }
}