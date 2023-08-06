package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.callbacks.PlayerJumpCallback;
import amymialee.peculiarpieces.component.PeculiarComponentInitializer;
import amymialee.peculiarpieces.component.WardingComponent;
import amymialee.peculiarpieces.items.GliderItem;
import amymialee.peculiarpieces.util.ExtraPlayerDataWrapper;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtOps;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements ExtraPlayerDataWrapper {
    @Unique private Vec3d checkpointPos;
    @Unique private RegistryKey<World> checkpointWorld;
    @Unique private int gameModeDuration = 0;
    @Unique private GameMode storedGameMode = null;
    @Unique private double bouncePower = 0;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "isBlockBreakingRestricted", at = @At("HEAD"), cancellable = true)
    public void PeculiarPieces$RestrictWardedBlock(World world, BlockPos pos, GameMode gameMode, CallbackInfoReturnable<Boolean> cir) {
        var component = PeculiarComponentInitializer.WARDING.maybeGet(world.getChunk(pos));
        if (component.isPresent()) {
            var wardingComponent = component.get();
            if (wardingComponent.getWard(pos)) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void PeculiarPieces$WriteCustomData(NbtCompound nbt, CallbackInfo ci) {
        if (this.checkpointPos != null && this.checkpointPos.distanceTo(Vec3d.ZERO) > 1) {
            nbt.put("pp:checkpos", NbtHelper.fromBlockPos(BlockPos.ofFloored(this.checkpointPos)));
        }
        if (this.checkpointWorld != null) {
            World.CODEC.encodeStart(NbtOps.INSTANCE, this.checkpointWorld).resultOrPartial(error -> {}).ifPresent(nbtElement -> nbt.put("CheckpointDimension", nbtElement));
        }
        if (this.storedGameMode != null) {
            nbt.putInt("pp:gamemode", this.storedGameMode.getId() + 1);
        }
        if (this.gameModeDuration != 0) {
            nbt.putInt("pp:gamemode_duration", this.gameModeDuration);
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void PeculiarPieces$ReadCustomData(NbtCompound nbt, CallbackInfo ci) {
        var vec3d = Vec3d.ofBottomCenter(NbtHelper.toBlockPos(nbt.getCompound("pp:checkpos")));
        if (vec3d.distanceTo(Vec3d.ZERO) > 1) {
            this.checkpointPos = vec3d;
        }
        var worldKey = getCheckpointDimension(nbt);
        worldKey.ifPresent(worldRegistryKey -> this.checkpointWorld = worldRegistryKey);
        var gameMode = nbt.getInt("pp:gamemode");
        if (gameMode != 0) {
            this.storedGameMode = GameMode.byId(gameMode - 1);
        } else {
            this.storedGameMode = null;
        }
        this.gameModeDuration = nbt.getInt("pp:gamemode_duration");
    }

    private static Optional<RegistryKey<World>> getCheckpointDimension(NbtCompound nbt) {
        return World.CODEC.parse(NbtOps.INSTANCE, nbt.get("CheckpointDimension")).result();
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void PeculiarPieces$GamemodeTicks(CallbackInfo ci) {
        if (((PlayerEntity) ((Object) this)) instanceof ServerPlayerEntity serverPlayer) {
            if (this.gameModeDuration > 0) {
                this.gameModeDuration--;
                if (this.gameModeDuration == 0) {
                    if (this.storedGameMode != null) {
                        serverPlayer.changeGameMode(this.storedGameMode);
                        this.storedGameMode = null;
                    }
                }
            }
        }
    }

    @Inject(method = "jump", at = @At("TAIL"))
    public void PeculiarPieces$JumpCallback(CallbackInfo ci) {
        if (!this.getWorld().isClient()) {
            PlayerJumpCallback.EVENT.invoker().onJump(((PlayerEntity) ((Object) this)), this.getWorld());
        }
    }

    @Inject(method = "tickMovement", at = @At("TAIL"))
    public void PeculiarPieces$MovementTicks(CallbackInfo ci) {
        var this2 = ((PlayerEntity) ((Object) this));
        var velocity = getVelocity();
        if (getBouncePower() > 0) {
            var f = this.headYaw * ((float)Math.PI / 180);
            this.setVelocity(this.getVelocity().add(-MathHelper.sin(f) * (velocity.horizontalLength() * getBouncePower() * 4) * (this.isSprinting() ? 1.5f : 0.5f), getBouncePower(), MathHelper.cos(f) * (velocity.horizontalLength() * getBouncePower() * 4) * (isSprinting() ? 1.5f : 0.5f)));
            setBouncePower(0);
            this.velocityDirty = true;
        } else if (GliderItem.isGliding(this2)) {
            var horizontalSpeed = !isSneaking() ? 0.03 : 0.1;
            var xSpeed = Math.cos(Math.toRadians(this.headYaw + 90)) * horizontalSpeed;
            var zSpeed = Math.sin(Math.toRadians(this.headYaw + 90)) * horizontalSpeed;
            this.setVelocity(velocity.x + xSpeed, !isSneaking() ? -0.052 : -0.176, velocity.z + zSpeed);
            this.fallDistance = 0;
        }
    }

    @Override public Vec3d getCheckpointPos() {
        return this.checkpointPos;
    }

    @Override public void setCheckpointPos(Vec3d vec3d) {
        this.checkpointPos = vec3d;
    }

    @Override public int getGameModeDuration() {
        return this.gameModeDuration;
    }

    @Override public void setGameModeDuration(int duration) {
        this.gameModeDuration = duration;
    }

    @Override public GameMode getStoredGameMode() {
        return this.storedGameMode;
    }

    @Override public void setStoredGameMode(GameMode gameMode) {
        this.storedGameMode = gameMode;
    }

    @Override public double getBouncePower() {
        return this.bouncePower;
    }

    @Override public void setBouncePower(double bouncePower) {
        this.bouncePower = bouncePower;
    }

    @Override
    public RegistryKey<World> getCheckpointWorld() {
        return this.checkpointWorld;
    }

    @Override
    public void setCheckpointWorld(RegistryKey<World> world) {
        this.checkpointWorld = world;
    }
}