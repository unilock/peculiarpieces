package amymialee.peculiarpieces.blocks;

import amymialee.peculiarpieces.registry.PeculiarBlocks;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.Fertilizable;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.Waterloggable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class LivingLadderBlock extends Block implements Fertilizable, Waterloggable {
    public static final DirectionProperty FACING = HorizontalFacingBlock.FACING;
    public static final BooleanProperty WATERLOGGED = Properties.WATERLOGGED;
    public static final BooleanProperty SECURE = BooleanProperty.of("secure");
    protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 3.0, 16.0, 16.0);
    protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(13.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 3.0);
    protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 13.0, 16.0, 16.0, 16.0);

    public LivingLadderBlock(AbstractBlock.Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(SECURE, false).with(WATERLOGGED, false));
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)) {
            case NORTH -> {
                return NORTH_SHAPE;
            }
            case SOUTH -> {
                return SOUTH_SHAPE;
            }
            case WEST -> {
                return WEST_SHAPE;
            }
        }
        return EAST_SHAPE;
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext entityShapeContext) {
            if (!(entityShapeContext.getEntity() instanceof LivingEntity)) {
                return VoxelShapes.empty();
            }
        }
        return super.getCollisionShape(state, world, pos, context);
    }

    @Override
    public boolean isShapeFullCube(BlockState state, BlockView world, BlockPos pos) {
        return false;
    }

    @Override
    public boolean hasRandomTicks(BlockState state) {
        return state.get(SECURE);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
        if (world.isInBuildLimit(pos.down())) {
            if (world.getBlockState(pos.down()).isAir()) {
                world.setBlockState(pos.down(), PeculiarBlocks.LIVING_LADDER.getDefaultState()
                        .with(SECURE, this.isSecurePlacement(world, pos.down(), state.get(FACING)))
                        .with(FACING, state.get(FACING))
                        .with(WATERLOGGED, world.isWater(pos.down())), Block.NOTIFY_ALL);
            }
        }
    }

    private boolean secureNear(WorldAccess world, BlockPos pos) {
        var safe = false;
        for (var i = pos.getY() - 1; i > world.getBottomY(); i--) {
            var stateDown = world.getBlockState(pos.withY(i));
            if (stateDown.isOf(this)) {
                if (stateDown.get(SECURE)) {
                    safe = true;
                    break;
                }
            } else {
                break;
            }
        }
        if (!safe) {
            for (var i = pos.getY() + 1; i < world.getTopY(); i++) {
                var stateUp = world.getBlockState(pos.withY(i));
                if (stateUp.isOf(this)) {
                    if (stateUp.get(SECURE)) {
                        safe = true;
                        break;
                    }
                } else {
                    break;
                }
            }
        }
        return safe;
    }

    @Override
    public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
        var secure = this.isSecurePlacement(world, pos, state.get(FACING));
        if (state.get(SECURE) != secure) {
            return state.with(SECURE, secure);
        }
        if (!state.get(SECURE)) {
            if (!this.secureNear(world, pos)) {
                return Blocks.AIR.getDefaultState();
            }
        }
        if (state.get(WATERLOGGED)) {
            world.scheduleFluidTick(pos, Fluids.WATER, Fluids.WATER.getTickRate(world));
        }
        return super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
    }

    @Override
    public boolean canReplace(BlockState state, ItemPlacementContext context) {
        return context.getStack().isOf(this.asItem());
    }

    @Override
    @Nullable
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        var world = ctx.getWorld();
        var pos = ctx.getBlockPos();
        var dir = ctx.getSide().getAxis() != Direction.Axis.Y ? ctx.getSide() : ctx.getHorizontalPlayerFacing();
        if (!this.isSecurePlacement(world, pos, dir)) {
            if (!this.secureNear(world, pos)) {
                return null;
            }
        }
        var blockState = ctx.getWorld().getBlockState(ctx.getBlockPos().offset(ctx.getSide().getOpposite()));
        if (!ctx.canReplaceExisting() && blockState.isOf(this) && blockState.get(FACING) == ctx.getSide()) {
            return null;
        }
        blockState = this.getDefaultState();
        var fluidState = ctx.getWorld().getFluidState(ctx.getBlockPos());
        return blockState.with(FACING, dir).with(SECURE, this.isSecurePlacement(world, pos, blockState.get(FACING).getOpposite())).with(WATERLOGGED, fluidState.getFluid() == Fluids.WATER);
    }

    private boolean isSecurePlacement(BlockView world, BlockPos pos, Direction side) {
        var blockState = world.getBlockState(pos.offset(side.getOpposite()));
        return blockState.isSideSolidFullSquare(world, pos, side.getOpposite());
    }

    @Override
    public boolean isFertilizable(WorldView world, BlockPos pos, BlockState state, boolean isClient) {
        var stateDown = world.getBlockState(pos.down());
        return stateDown.isAir() || ((stateDown.getBlock() instanceof LivingLadderBlock livingLadderBlock) && livingLadderBlock.isFertilizable(world, pos.down(), state, isClient));
    }

    @Override
    public boolean canGrow(World world, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public void grow(ServerWorld world, Random random, BlockPos pos, BlockState state) {
        if (world.isInBuildLimit(pos.down())) {
            var below = world.getBlockState(pos.down());
            if (below.isOf(this)) {
                ((Fertilizable) below.getBlock()).grow(world, random, pos.down(), below);
            } else {
                world.setBlockState(pos.up(), PeculiarBlocks.LIVING_LADDER.getDefaultState()
                        .with(SECURE, this.isSecurePlacement(world, pos.down(), state.get(FACING)))
                        .with(FACING, state.get(FACING))
                        .with(WATERLOGGED, world.isWater(pos.up())), Block.NOTIFY_ALL);
            }
        }
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    public FluidState getFluidState(BlockState state) {
        if (state.get(WATERLOGGED)) {
            return Fluids.WATER.getStill(false);
        }
        return super.getFluidState(state);
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, WATERLOGGED, SECURE);
    }
}