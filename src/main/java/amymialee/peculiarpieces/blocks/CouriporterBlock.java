package amymialee.peculiarpieces.blocks;

import amymialee.peculiarpieces.blockentities.CouriporterBlockEntity;
import amymialee.peculiarpieces.items.PositionPearlItem;
import amymialee.peculiarpieces.util.BlockEntityWrapper;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.BlockWithEntity;
import net.minecraft.block.Blocks;
import net.minecraft.block.FacingBlock;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.ActionResult;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Hand;
import net.minecraft.util.ItemScatterer;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;

public class CouriporterBlock extends BlockWithEntity {
    public static final DirectionProperty FACING = FacingBlock.FACING;
    public static final BooleanProperty TRIGGERED = Properties.TRIGGERED;
    protected static final VoxelShape BOTTOM_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 7.0, 16.0);
    protected static final VoxelShape TOP_SHAPE = Block.createCuboidShape(0.0, 9.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape SOUTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 9.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape NORTH_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 07.0);
    protected static final VoxelShape EAST_SHAPE = Block.createCuboidShape(9.0, 0.0, 0.0, 16.0, 16.0, 16.0);
    protected static final VoxelShape WEST_SHAPE = Block.createCuboidShape(0.0, 0.0, 0.0, 07.0, 16.0, 16.0);

    public CouriporterBlock(Settings settings) {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.NORTH).with(TRIGGERED, false));
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        switch (state.get(FACING)) {
            case UP -> {
                return TOP_SHAPE;
            }
            case EAST -> {
                return EAST_SHAPE;
            }
            case WEST -> {
                return WEST_SHAPE;
            }
            case NORTH -> {
                return NORTH_SHAPE;
            }
            case SOUTH -> {
                return SOUTH_SHAPE;
            }
        }
        return BOTTOM_SHAPE;
    }

    public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
        return new CouriporterBlockEntity(pos, state);
    }

    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack itemStack) {
        if (itemStack.hasCustomName()) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CouriporterBlockEntity couriporter) {
                couriporter.setCustomName(itemStack.getName());
            }
        }
    }

    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        } else {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CouriporterBlockEntity couriporterBlockEntity) {
                if (player.isSneaking() && player.getAbilities().allowModifyWorld) {
                    player.openHandledScreen(couriporterBlockEntity);
                }
            }
            return ActionResult.CONSUME;
        }
    }

    protected void ignite(World world, BlockState state, BlockPos pos) {
        if (world.getBlockEntity(pos) instanceof CouriporterBlockEntity couriporter) {
            ItemStack stack = couriporter.getInventory().get(0);
            NbtCompound compound = stack.getNbt();
            if (compound != null && compound.contains("pp:target")) {
                BlockPos targetPos = PositionPearlItem.readStone(stack);
                if (world.getBlockState(targetPos).isAir()) {
                    BlockPos movingPos = pos.add(state.get(FACING).getVector());
                    BlockState movingState = world.getBlockState(movingPos);
                    if (!movingState.isAir()) {
                        world.setBlockState(targetPos, movingState);
                        BlockEntity entity = world.getBlockEntity(movingPos);
                        if (entity != null) {
                            world.removeBlockEntity(movingPos);
                            ((BlockEntityWrapper) entity).setPos(targetPos);
                            world.addBlockEntity(entity);
                        }
                        world.createAndScheduleBlockTick(targetPos, movingState.getBlock(), 2);
                        world.setBlockState(movingPos, Blocks.AIR.getDefaultState());
                        world.playSound(null, movingPos, SoundEvents.ENTITY_SHULKER_TELEPORT, SoundCategory.BLOCKS, 1.0f, world.getRandom().nextFloat() * 0.4f + 0.8f);
                        world.playSound(null, targetPos, SoundEvents.ENTITY_SHULKER_TELEPORT, SoundCategory.BLOCKS, 1.0f, world.getRandom().nextFloat() * 0.4f + 0.8f);
                        if (world instanceof ServerWorld serverWorld) {
                            {
                                double r = movingPos.getX() + 0.5f;
                                double s = movingPos.getY() + 0.5f;
                                double d = movingPos.getZ() + 0.5f;
                                for (double e = 0.0; e < Math.PI * 2; e += 0.15707963267948966) {
                                    serverWorld.spawnParticles(ParticleTypes.PORTAL, r + Math.cos(e) * 0.2f, s, d + Math.sin(e) * 0.2f, 2, 0, 0, 0, Math.cos(e) * -5.0);
                                    serverWorld.spawnParticles(ParticleTypes.PORTAL, r + Math.cos(e) * 0.2f, s, d + Math.sin(e) * 0.2f, 2, 0, 0, 0, Math.sin(e) * -7.0);
                                }
                            }
                            {
                                double r = targetPos.getX() + 0.5f;
                                double s = targetPos.getY() + 0.5f;
                                double d = targetPos.getZ() + 0.5f;
                                for (double e = 0.0; e < Math.PI * 2; e += 0.15707963267948966) {
                                    serverWorld.spawnParticles(ParticleTypes.PORTAL, r + Math.cos(e) * 0.2f, s, d + Math.sin(e) * 0.2f, 2, 0, 0, 0, Math.cos(e) * -5.0);
                                    serverWorld.spawnParticles(ParticleTypes.PORTAL, r + Math.cos(e) * 0.2f, s, d + Math.sin(e) * 0.2f, 2, 0, 0, 0, Math.sin(e) * -7.0);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public void neighborUpdate(BlockState state, World world, BlockPos pos, Block sourceBlock, BlockPos sourcePos, boolean notify) {
        boolean bl = world.isReceivingRedstonePower(pos) || world.isReceivingRedstonePower(pos.up());
        boolean bl2 = state.get(TRIGGERED);
        if (bl && !bl2) {
            this.ignite(world, state, pos);
            world.setBlockState(pos, state.with(TRIGGERED, true), Block.NO_REDRAW);
        } else if (!bl && bl2) {
            world.setBlockState(pos, state.with(TRIGGERED, false), Block.NO_REDRAW);
        }
    }

    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection());
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved) {
        if (!state.isOf(newState.getBlock())) {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof CouriporterBlockEntity) {
                ItemScatterer.spawn(world, pos, (Inventory) blockEntity);
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.MODEL;
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
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING, TRIGGERED);
    }
}