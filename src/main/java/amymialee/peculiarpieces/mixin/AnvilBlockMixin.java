package amymialee.peculiarpieces.mixin;

import net.minecraft.block.AnvilBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AnvilBlock.class)
public class AnvilBlockMixin {
    @Inject(method = "getLandingState", at = @At("HEAD"), cancellable = true)
    private static void PeculiarPieces$AnvilFallNoBreak(BlockState fallingState, CallbackInfoReturnable<BlockState> cir) {
        cir.setReturnValue(fallingState);
    }

    @Inject(method = "onLanding", at = @At("HEAD"))
    public void PeculiarPieces$RenewableSand(World world, BlockPos pos, BlockState fallingBlockState, BlockState currentStateInPos, FallingBlockEntity fallingBlockEntity, CallbackInfo ci) {
        var underState = world.getBlockState(pos.down());
        if (underState.getBlock() == Blocks.COBBLESTONE) {
            world.setBlockState(pos.down(), Blocks.SAND.getDefaultState());
        } else if (underState.getBlock() == Blocks.PUMPKIN) {
            world.setBlockState(pos.down(), Blocks.RED_SAND.getDefaultState());
        }
    }
}