package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.component.PeculiarComponentInitializer;
import amymialee.peculiarpieces.component.WardingComponent;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PistonBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(PistonBlock.class)
public class PistonBlockMixin extends Block {
    public PistonBlockMixin(Settings settings) {
        super(settings);
    }

    @Inject(method = "isMovable", at = @At("HEAD"), cancellable = true)
    private static void PeculiarPieces$Unmovable(BlockState state, World world, BlockPos pos, Direction direction, boolean canBreak, Direction pistonDir, CallbackInfoReturnable<Boolean> cir) {
        var component = PeculiarComponentInitializer.WARDING.maybeGet(world.getChunk(pos));
        if (component.isPresent()) {
            var wardingComponent = component.get();
            if (wardingComponent.getWard(pos)) {
                cir.setReturnValue(false);
            }
        }
    }
}