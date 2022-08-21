package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.component.PeculiarComponentInitializer;
import amymialee.peculiarpieces.component.WardingComponent;
import amymialee.peculiarpieces.util.RedstoneInstance;
import amymialee.peculiarpieces.util.RedstoneManager;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.chunk.WorldChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(World.class)
public abstract class WorldMixin implements WorldAccess {
    @Shadow public abstract WorldChunk getChunk(int i, int j);

    @Inject(method = "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;II)Z", at = @At("HEAD"), cancellable = true)
    public void PeculiarPieces$RestrictWardedOverride(BlockPos pos, BlockState state, int flags, int maxUpdateDepth, CallbackInfoReturnable<Boolean> cir) {
        Optional<WardingComponent> component = PeculiarComponentInitializer.WARDING.maybeGet(this.getChunk(pos));
        if (component.isPresent()) {
            WardingComponent wardingComponent = component.get();
            if (wardingComponent.getWard(pos)) {
                cir.setReturnValue(false);
            }
        }
    }

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