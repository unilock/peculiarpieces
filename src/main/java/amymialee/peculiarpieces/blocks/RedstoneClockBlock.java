package amymialee.peculiarpieces.blocks;

import amymialee.peculiarpieces.util.PeculiarHelper;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.tick.TickPriority;

public class RedstoneClockBlock extends AbstractRedstoneComparisonBlock {
    public static final IntProperty DELAY = IntProperty.of("delay", 0, 4);
    public static final BooleanProperty LOOPED = BooleanProperty.of("looped");

    public RedstoneClockBlock(FabricBlockSettings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(DELAY, 1).with(POWERED, false));
    }

    @Override
    protected int getUpdateDelayInternal(BlockState state) {
        return Math.max(1, state.get(DELAY) * 2);
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!player.getAbilities().allowModifyWorld) {
            return ActionResult.PASS;
        }
        int filter = PeculiarHelper.clampLoop(0, 4, state.get(DELAY) + (player.isSneaking() ? -1 : 1));
        world.setBlockState(pos, state.with(DELAY, filter), Block.NOTIFY_ALL);
        world.playSound(player, pos, SoundEvents.BLOCK_COMPARATOR_CLICK, SoundCategory.BLOCKS, 0.3f, (float) (3 * filter) / 15);
        return ActionResult.success(world.isClient);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (this.isLocked(world, pos, state)) {
            return;
        }
        boolean bl2 = this.hasPower(world, pos, state);
        if (bl2) {
            world.setBlockState(pos, state.with(POWERED, true).with(LOOPED, !state.get(LOOPED)), Block.NOTIFY_LISTENERS);
            world.scheduleBlockTick(pos, this, this.getUpdateDelayInternal(state), TickPriority.VERY_HIGH);
        } else {
            world.setBlockState(pos, state.with(POWERED, false).with(LOOPED, false), Block.NOTIFY_LISTENERS);
        }
    }

    @Override
    protected int getOutputLevel(BlockView world, BlockPos pos, BlockState state) {
        return state.get(LOOPED) ? 0 : 15;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, POWERED, DELAY, LOOPED);
    }
}