package amymialee.peculiarpieces.blocks;

import amymialee.peculiarpieces.blockentities.RedstoneTriggerBlockEntity;
import amymialee.peculiarpieces.items.RedstoneRemoteItem;
import amymialee.peculiarpieces.registry.PeculiarBlocks;
import amymialee.peculiarpieces.util.RedstoneInstance;
import amymialee.peculiarpieces.util.RedstoneManager;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class RedstoneTriggerBlock extends BlockWithEntity {
    public static final BooleanProperty TRIGGERED = Properties.TRIGGERED;
    protected static final VoxelShape SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0);

    public RedstoneTriggerBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(TRIGGERED, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new RedstoneTriggerBlockEntity(pos, state);
    }

    @Override
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (itemStack.hasCustomName()) {
            var blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof RedstoneTriggerBlockEntity redstoneTriggerEntity) {
                redstoneTriggerEntity.setCustomName(itemStack.getName());
            }
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        var blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof RedstoneTriggerBlockEntity redstoneTriggerEntity) {
            if (player.getAbilities().allowModifyWorld) {
                player.openHandledScreen(redstoneTriggerEntity);
            }
        }
        return ActionResult.CONSUME;
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            var blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof RedstoneTriggerBlockEntity) {
                ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        var bl = world.isReceivingRedstonePower(pos);
        if (bl != state.get(TRIGGERED)) {
            world.setBlockState(pos, state.with(TRIGGERED, bl), Block.NOTIFY_ALL);
            if (!world.isClient) {
                var blockEntity = world.getBlockEntity(pos);
                if (blockEntity instanceof RedstoneTriggerBlockEntity redstoneTriggerEntity) {
                    var itemStack = redstoneTriggerEntity.getStack(bl ? 0 : 1);
                    var target = RedstoneRemoteItem.readTarget(itemStack);
                    if (target != BlockPos.ORIGIN) {
                        RedstoneManager.addInstance(world, target, new RedstoneInstance());
                    }
                }
            }
        }
    }

    @Override
    @Nullable
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type) {
        return world.isClient ? RedstoneTriggerBlock.checkType(type, PeculiarBlocks.REDSTONE_TRIGGER_BLOCK_ENTITY, RedstoneTriggerBlockEntity::tick) : null;
    }

    @Override
    public boolean emitsRedstonePower(BlockState state) {
        return true;
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(TRIGGERED);
    }
}