package amymialee.peculiarpieces.items;

import amymialee.peculiarpieces.blocks.LivingLadderBlock;
import amymialee.peculiarpieces.registry.PeculiarBlocks;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class LivingLadderItem extends BlockItem {
    public LivingLadderItem(Block block, FabricItemSettings settings) {
        super(block, settings);
    }

    @Override
    @Nullable
    public ItemPlacementContext getPlacementContext(ItemPlacementContext context) {
        var blockPos = context.getBlockPos();
        var world = context.getWorld();
        var blockState = world.getBlockState(blockPos);
        if (blockState.isOf(PeculiarBlocks.LIVING_LADDER)) {
            var stateDir = blockState.get(LivingLadderBlock.FACING);
            var direction = context.getPlayer() != null && context.getPlayer().isSneaking() ? Direction.UP : Direction.DOWN;
            var mutable = blockPos.mutableCopy().move(direction);
            while (true) {
                if (!world.isClient && !world.isInBuildLimit(mutable)) {
                    break;
                }
                blockState = world.getBlockState(mutable);
                if (!blockState.isOf(this.getBlock())) {
                    if (!blockState.canReplace(context)) break;
                    return ItemPlacementContext.offset(context, mutable, stateDir);
                }
                mutable.move(direction);
            }
            return null;
        }
        return context;
    }

    @Override
    protected boolean checkStatePlacement() {
        return false;
    }
}