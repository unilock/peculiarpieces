package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.component.PeculiarComponentInitializer;
import amymialee.peculiarpieces.component.WardingComponent;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(World.class)
public abstract class WorldMixin implements WorldAccess {

    @Inject(method = "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;II)Z", at = @At("HEAD"), cancellable = true)
    public void PeculiarPieces$RestrictWardedOverride(BlockPos pos, BlockState state, int flags, int maxUpdateDepth, CallbackInfoReturnable<Boolean> cir) {
        Optional<WardingComponent> component = PeculiarComponentInitializer.WARDING.maybeGet(this.getChunk(pos));
        if (component.isPresent()) {
            WardingComponent wardingComponent = component.get();
            BlockState current = this.getBlockState(pos);
            if (wardingComponent.getWard(this, pos)) {
                if (current.getBlock() != state.getBlock()) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}