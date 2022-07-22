package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.util.RedstoneInstance;
import amymialee.peculiarpieces.util.RedstoneManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public class WorldMixin {
    @Inject(method = "isReceivingRedstonePower", at = @At("HEAD"), cancellable = true)
    public void PeculiarPieces$RedstoneInstances(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        World world = ((World) ((Object) this));
        if (RedstoneManager.isPowered(world, pos)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "getReceivedRedstonePower", at = @At("RETURN"), cancellable = true)
    public void PeculiarPieces$RedstoneInstancePower(BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        World world = ((World) ((Object) this));
        int instancePower = RedstoneManager.getPower(world, pos);
        if (cir.getReturnValue() < instancePower) {
            cir.setReturnValue(instancePower);
        }
    }

    @Inject(method = "getReceivedStrongRedstonePower", at = @At("RETURN"), cancellable = true)
    public void PeculiarPieces$StrongRedstoneInstancePower(BlockPos pos, CallbackInfoReturnable<Integer> cir) {
        World world = ((World) ((Object) this));
        RedstoneInstance instance = RedstoneManager.getInstance(world, pos);
        if (instance != null) {
            if (instance.isStrong()) {
                int instancePower = instance.getPower();
                if (cir.getReturnValue() < instancePower) {
                    cir.setReturnValue(instancePower);
                }
            }
        }
    }
}