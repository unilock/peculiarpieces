package amymialee.peculiarpieces.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(FluidBlock.class)
public abstract class FluidBlockMixin {
    @Shadow @Final protected FlowableFluid fluid;
    @Shadow @Final public static ImmutableList<Direction> FLOW_DIRECTIONS;
    @Shadow protected abstract void playExtinguishSound(WorldAccess world, BlockPos pos);

    @Inject(method = "receiveNeighborFluids", at = @At("HEAD"), cancellable = true)
    private void PeculiarPieces$RenewableDeepslate(World world, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (this.fluid.isIn(FluidTags.LAVA)) {
            if (world.getBlockState(pos.down()).isOf(Blocks.DEEPSLATE)) {
                for (var direction : FLOW_DIRECTIONS) {
                    var blockPos = pos.offset(direction.getOpposite());
                    if (world.getFluidState(blockPos).isIn(FluidTags.WATER) || world.getBlockState(blockPos).isOf(Blocks.BLUE_ICE)) {
                        var block = world.getFluidState(pos).isStill() ? Blocks.OBSIDIAN : Blocks.DEEPSLATE;
                        world.setBlockState(pos, block.getDefaultState());
                        this.playExtinguishSound(world, pos);
                        cir.setReturnValue(false);
                    }
                }
            } else if (world.getBlockState(pos.down()).isOf(Blocks.GRANITE)) {
                for (var direction : FLOW_DIRECTIONS) {
                    var blockPos = pos.offset(direction.getOpposite());
                    if (world.getFluidState(blockPos).isIn(FluidTags.WATER) || world.getBlockState(blockPos).isOf(Blocks.BLUE_ICE)) {
                        var block = world.getFluidState(pos).isStill() ? Blocks.OBSIDIAN : Blocks.GRANITE;
                        world.setBlockState(pos, block.getDefaultState());
                        this.playExtinguishSound(world, pos);
                        cir.setReturnValue(false);
                    }
                }
            } else if (world.getBlockState(pos.down()).isOf(Blocks.DIORITE)) {
                for (var direction : FLOW_DIRECTIONS) {
                    var blockPos = pos.offset(direction.getOpposite());
                    if (world.getFluidState(blockPos).isIn(FluidTags.WATER) || world.getBlockState(blockPos).isOf(Blocks.BLUE_ICE)) {
                        var block = world.getFluidState(pos).isStill() ? Blocks.OBSIDIAN : Blocks.DIORITE;
                        world.setBlockState(pos, block.getDefaultState());
                        this.playExtinguishSound(world, pos);
                        cir.setReturnValue(false);
                    }
                }
            } else if (world.getBlockState(pos.down()).isOf(Blocks.ANDESITE)) {
                for (var direction : FLOW_DIRECTIONS) {
                    var blockPos = pos.offset(direction.getOpposite());
                    if (world.getFluidState(blockPos).isIn(FluidTags.WATER) || world.getBlockState(blockPos).isOf(Blocks.BLUE_ICE)) {
                        var block = world.getFluidState(pos).isStill() ? Blocks.OBSIDIAN : Blocks.ANDESITE;
                        world.setBlockState(pos, block.getDefaultState());
                        this.playExtinguishSound(world, pos);
                        cir.setReturnValue(false);
                    }
                }
            }
        }
    }
}