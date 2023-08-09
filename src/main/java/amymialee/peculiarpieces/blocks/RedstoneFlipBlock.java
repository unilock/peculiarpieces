package amymialee.peculiarpieces.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RepeaterBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.minecraft.world.tick.TickPriority;

public class RedstoneFlipBlock extends AbstractRedstoneComparisonBlock {
    public static final BooleanProperty ENABLED = BooleanProperty.of("enabled");

    public RedstoneFlipBlock(FabricBlockSettings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(ENABLED, false).with(POWERED, false));
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        if (state.getBlock() instanceof RedstoneFlipBlock) {
            var blockState = state.cycle(ENABLED);
            world.setBlockState(pos, blockState, Block.NOTIFY_ALL);
            world.updateNeighborsAlways(pos, this);
            world.updateNeighborsAlways(pos.offset(blockState.get(FACING).getOpposite()), this);
            var f = blockState.get(ENABLED) ? 0.6f : 0.5f;
            world.playSound(null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3f, f);
            world.emitGameEvent(player, blockState.get(ENABLED) ? GameEvent.BLOCK_ACTIVATE : GameEvent.BLOCK_DEACTIVATE, pos);
        }
        return ActionResult.CONSUME;
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (this.isLocked(world, pos, state)) {
            return;
        }
        if (this.hasPower(world, pos, state)) {
            if (!state.get(POWERED)) {
                world.setBlockState(pos, state.with(POWERED, true).with(ENABLED, !state.get(ENABLED)), Block.NOTIFY_LISTENERS);
                world.playSound(null, pos, SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.BLOCKS, 0.3f, world.getBlockState(pos).get(ENABLED) ? 0.6f : 0.5f);
                world.scheduleBlockTick(pos, this, this.getUpdateDelayInternal(state), TickPriority.VERY_HIGH);
            }
        } else {
            world.setBlockState(pos, state.with(POWERED, false), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction) {
        if (state.get(ENABLED) && state.get(FACING) == direction) {
            return this.getOutputLevel(world, pos, state);
        }
        return 0;
    }

    @Override
    public void randomDisplayTick(BlockState state, World world, BlockPos pos, Random random) {
        if (!state.get(ENABLED)) {
            return;
        }
        super.randomDisplayTick(state, world, pos, random);
    }

    @Override
    protected int getOutputLevel(BlockView world, BlockPos pos, BlockState state) {
        return 15;
    }

    @Override
    protected boolean getSideInputFromGatesOnly() {
        return true;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, ENABLED);
    }
}