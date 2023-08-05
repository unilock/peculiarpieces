package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.callbacks.PlayerCrouchCallback;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@SuppressWarnings("ConstantConditions")
@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);
    @Shadow protected abstract boolean getFlag(int index);
    @Shadow public abstract boolean isSneaking();
    @Shadow public abstract World getWorld();
    @Shadow public abstract EntityDimensions getDimensions(EntityPose pose);

    @Unique
    private boolean wasSneaky = false;

    @Inject(method = "pushAwayFrom", at = @At("HEAD"), cancellable = true)
    private void PeculiarPieces$DisablePushing(Entity entity, CallbackInfo ci) {
        if (!entity.getWorld().getGameRules().getBoolean(PeculiarPieces.NO_MOB_PUSHING)) {
            ci.cancel();
        }
    }

    @Inject(method = "isSneaking", at = @At("HEAD"))
    public void PeculiarPieces$IsSneakingHead(CallbackInfoReturnable<Boolean> cir) {
        if (!this.getWorld().isClient()) {
            boolean sneaking = this.getFlag(1);
            if (sneaking != this.wasSneaky) {
                if (sneaking) {
                    this.wasSneaky = true;
                    if ((Object) this instanceof PlayerEntity player) {
                        PlayerCrouchCallback.EVENT.invoker().onCrouch(player, this.getWorld());
                    }
                } else {
                    this.wasSneaky = false;
                }
            }
        }
    }

    @Inject(method = "getMountedHeightOffset", at = @At("HEAD"), cancellable = true)
    public void PeculiarPieces$MountedHeightOffsetHead(CallbackInfoReturnable<Double> cir) {
        if ((Object) this instanceof PlayerEntity) {
            cir.setReturnValue((double) this.getDimensions(EntityPose.STANDING).height);
        }
    }
}