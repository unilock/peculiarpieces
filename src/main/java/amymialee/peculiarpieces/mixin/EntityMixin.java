package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.PeculiarPieces;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract void playSound(SoundEvent sound, float volume, float pitch);
    @Shadow public World world;
    @Shadow public abstract void onLanding();
    @Shadow public abstract BlockState getBlockStateAtPos();
    @Shadow protected abstract boolean getFlag(int index);
    @Shadow public float fallDistance;
    @Shadow public abstract boolean isSneaking();
    @Shadow public abstract Vec3d getVelocity();
    @Shadow public boolean velocityDirty;
    @Shadow public abstract void setVelocity(Vec3d velocity);
    @Shadow public abstract boolean isSprinting();
    @Shadow public abstract void setVelocity(double x, double y, double z);
    @Shadow public abstract ItemEntity dropStack(ItemStack stack);
    @Shadow public abstract void discard();

    @Inject(method = "pushAwayFrom", at = @At("HEAD"), cancellable = true)
    private void PeculiarPieces$DisablePushing(Entity entity, CallbackInfo ci) {
        if (!entity.world.getGameRules().getBoolean(PeculiarPieces.NO_MOB_PUSHING)) {
            ci.cancel();
        }
    }

    @Inject(method = "isSneaking", at = @At("HEAD"))
    public void PeculiarPieces$IsSneakingHead(CallbackInfoReturnable<Boolean> cir) {}

    @Inject(method = "getMountedHeightOffset", at = @At("HEAD"))
    public void PeculiarPieces$MountedHeightOffsetHead(CallbackInfoReturnable<Double> cir) {}
}