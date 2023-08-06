package amymialee.peculiarpieces.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
    @Shadow protected ClientWorld world;

    @Inject(method = "addBlockBreakingParticles", at = @At("HEAD"), cancellable = true)
    public void PeculiarPieces$NoEmptyParticles(BlockPos pos, Direction direction, CallbackInfo ci) {
        var blockState = this.world.getBlockState(pos);
        var shape = blockState.getOutlineShape(this.world, pos);
        if (shape.isEmpty()) {
            ci.cancel();
        }
    }
}