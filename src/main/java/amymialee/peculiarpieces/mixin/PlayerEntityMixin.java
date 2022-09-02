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
    @Unique private int gameModeDuration = 0;
    @Unique private GameMode storedGameMode = null;
    @Unique private double bouncePower = 0;

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "isBlockBreakingRestricted", at = @At("HEAD"), cancellable = true)
    public void PeculiarPieces$RestrictWardedBlock(World world, BlockPos pos, GameMode gameMode, CallbackInfoReturnable<Boolean> cir) {
        Optional<WardingComponent> component = PeculiarComponentInitializer.WARDING.maybeGet(world.getChunk(pos));
        if (component.isPresent()) {
            WardingComponent wardingComponent = component.get();
            if (wardingComponent.getWard(pos)) {
                cir.setReturnValue(true);
            }
        }
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    public void PeculiarPieces$WriteCustomData(NbtCompound nbt, CallbackInfo ci) {
        if (checkpointPos != null && checkpointPos.distanceTo(Vec3d.ZERO) > 1) {
            nbt.put("pp:checkpos", NbtHelper.fromBlockPos(new BlockPos(checkpointPos)));
        }
        if (storedGameMode != null) {
            nbt.putInt("pp:gamemode", storedGameMode.getId() + 1);
        }
        if (gameModeDuration != 0) {
            nbt.putInt("pp:gamemode_duration", gameModeDuration);
        }
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    public void PeculiarPieces$ReadCustomData(NbtCompound nbt, CallbackInfo ci) {
        Vec3d vec3d = Vec3d.ofBottomCenter(NbtHelper.toBlockPos(nbt.getCompound("pp:checkpos")));
        if (vec3d.distanceTo(Vec3d.ZERO) > 1) {
            checkpointPos = vec3d;
        }
        int gameMode = nbt.getInt("pp:gamemode");
        if (gameMode != 0) {
            storedGameMode = GameMode.byId(gameMode - 1);
        } else {
            storedGameMode = null;
        }
        gameModeDuration = nbt.getInt("pp:gamemode_duration");
    }

    @Inject(method = "tick", at = @At("TAIL"))
    public void PeculiarPieces$GamemodeTicks(CallbackInfo ci) {
        if (((PlayerEntity) ((Object) this)) instanceof ServerPlayerEntity serverPlayer) {
            if (gameModeDuration > 0) {
                gameModeDuration--;
                if (gameModeDuration == 0) {
                    if (storedGameMode != null) {
                        serverPlayer.changeGameMode(storedGameMode);
                        storedGameMode = null;
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
        PlayerEntity this2 = ((PlayerEntity) ((Object) this));
        Vec3d velocity = getVelocity();
        if (getBouncePower() > 0) {
            float f = this.headYaw * ((float)Math.PI / 180);
            this.setVelocity(this.getVelocity().add(-MathHelper.sin(f) * (velocity.horizontalLength() * getBouncePower() * 4) * (this.isSprinting() ? 1.5f : 0.5f), getBouncePower(), MathHelper.cos(f) * (velocity.horizontalLength() * getBouncePower() * 4) * (isSprinting() ? 1.5f : 0.5f)));
            setBouncePower(0);
            this.velocityDirty = true;
        } else if (GliderItem.isGliding(this2)) {
            double horizontalSpeed = !isSneaking() ? 0.03 : 0.1;
            double xSpeed = Math.cos(Math.toRadians(headYaw + 90)) * horizontalSpeed;
            double zSpeed = Math.sin(Math.toRadians(headYaw + 90)) * horizontalSpeed;
            this.setVelocity(velocity.x + xSpeed, !isSneaking() ? -0.052 : -0.176, velocity.z + zSpeed);
            this.fallDistance = 0;
        }
    }

    @Override public Vec3d getCheckpointPos() {
        return checkpointPos;
    }

    @Override public void setCheckpointPos(Vec3d vec3d) {
        checkpointPos = vec3d;
    }

    @Override public int getGameModeDuration() {
        return gameModeDuration;
    }

    @Override public void setGameModeDuration(int duration) {
        gameModeDuration = duration;
    }

    @Override public GameMode getStoredGameMode() {
        return storedGameMode;
    }

    @Override public void setStoredGameMode(GameMode gameMode) {
        storedGameMode = gameMode;
    }

    @Override public double getBouncePower() {
        return bouncePower;
    }

    @Override public void setBouncePower(double bouncePower) {
        this.bouncePower = bouncePower;
    }
}