package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.component.PeculiarComponentInitializer;
import amymialee.peculiarpieces.component.WardingComponent;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@SuppressWarnings("CancellableInjectionUsage")
@Mixin(AbstractBlock.class)
public class AbstractBlockMixin {
    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    public void PeculiarPieces$OnUseHead(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit, CallbackInfoReturnable<ActionResult> cir) {}

    @Inject(method = "canPlaceAt", at = @At("HEAD"), cancellable = true)
    public void PeculiarPieces$WardCanAlwaysPlaceAt(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        var component = PeculiarComponentInitializer.WARDING.maybeGet(world.getChunk(pos));
        if (component.isPresent()) {
            var wardingComponent = component.get();
            if (wardingComponent.getWard(pos)) {
                cir.setReturnValue(true);
            }
        }
    }

    @Mixin(AbstractBlock.AbstractBlockState.class)
    static abstract class AbstractBlockStateMixin {
        @Shadow public abstract Block getBlock();
        @Shadow protected abstract BlockState asBlockState();

        @Inject(method = "getStateForNeighborUpdate", at = @At("HEAD"), cancellable = true)
        public void PeculiarPieces$WardCantChange(Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos, CallbackInfoReturnable<BlockState> cir) {
            var component = PeculiarComponentInitializer.WARDING.maybeGet(world.getChunk(pos));
            if (component.isPresent()) {
                var wardingComponent = component.get();
                if (wardingComponent.getWard(pos)) {
                    if (this.getBlock() != this.getBlock().getStateForNeighborUpdate(this.asBlockState(), direction, neighborState, world, pos, neighborPos).getBlock()) {
                        cir.setReturnValue(this.asBlockState());
                    }
                }
            }
        }

        @Inject(method = "canPlaceAt", at = @At("HEAD"), cancellable = true)
        public void canPlaceAt(WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
            var component = PeculiarComponentInitializer.WARDING.maybeGet(world.getChunk(pos));
            if (component.isPresent()) {
                var wardingComponent = component.get();
                if (wardingComponent.getWard(pos)) {
                    cir.setReturnValue(true);
                }
            }
        }
    }
}