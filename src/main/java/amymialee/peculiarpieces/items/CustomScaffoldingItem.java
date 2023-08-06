package amymialee.peculiarpieces.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ScaffoldingBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CustomScaffoldingItem extends BlockItem {
    private final int size;

    public CustomScaffoldingItem(int size, Block block, FabricItemSettings settings) {
        super(block, settings);
        this.size = size;
    }

    @Override
    @Nullable
    public ItemPlacementContext getPlacementContext(ItemPlacementContext context) {
        var blockPos = context.getBlockPos();
        var world = context.getWorld();
        var blockState = world.getBlockState(blockPos);
        if (blockState.isOf(this.getBlock())) {
            var direction = context.shouldCancelInteraction() ? (context.hitsInsideBlock() ? context.getSide().getOpposite() : context.getSide()) : (context.getSide() == Direction.UP ? context.getHorizontalPlayerFacing() : Direction.UP);
            var i = 0;
            var mutable = blockPos.mutableCopy().move(direction);
            while (i < this.size) {
                if (!world.isClient && !world.isInBuildLimit(mutable)) {
                    var playerEntity = context.getPlayer();
                    var j = world.getTopY();
                    if (!(playerEntity instanceof ServerPlayerEntity) || mutable.getY() < j) break;
                    playerEntity.sendMessage(Text.translatable("build.tooHigh", j - 1).formatted(Formatting.RED), true);
                    break;
                }
                blockState = world.getBlockState(mutable);
                if (!blockState.isOf(this.getBlock())) {
                    if (!blockState.canReplace(context)) break;
                    return ItemPlacementContext.offset(context, mutable, direction);
                }
                mutable.move(direction);
                if (!direction.getAxis().isHorizontal()) continue;
                ++i;
            }
            return null;
        }
        if (ScaffoldingBlock.calculateDistance(world, blockPos) == this.size) {
            return null;
        }
        return context;
    }

    @Override
    protected boolean checkStatePlacement() {
        return false;
    }
}