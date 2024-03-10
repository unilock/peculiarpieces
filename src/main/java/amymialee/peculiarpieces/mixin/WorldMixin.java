package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.component.PeculiarChunkComponentInitializer;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class WorldMixin implements WorldAccess {

    @Inject(method = "setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;II)Z", at = @At("HEAD"), cancellable = true)
    public void PeculiarPieces$RestrictWardedOverride(BlockPos pos, BlockState state, int flags, int maxUpdateDepth, CallbackInfoReturnable<Boolean> cir) {
        var component = PeculiarChunkComponentInitializer.WARDING.maybeGet(this.getChunk(pos));
        if (component.isPresent()) {
            var wardingComponent = component.get();
            var current = this.getBlockState(pos);
            if (wardingComponent.getWard(this, pos)) {
                if (current.getBlock() != state.getBlock()) {
                    cir.setReturnValue(false);
                }
            }
        }
    }
}