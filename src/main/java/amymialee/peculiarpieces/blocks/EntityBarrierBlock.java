package amymialee.peculiarpieces.blocks;

import amymialee.peculiarpieces.VisibleBarriersAccess;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;

public class EntityBarrierBlock extends Block {
    private final boolean player;

    public EntityBarrierBlock(boolean player, FabricBlockSettings settings) {
        super(settings);
        this.player = player;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context.isHolding(this.asItem()) || VisibleBarriersAccess.areBarriersEnabled()) return VoxelShapes.fullCube();
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext entityShapeContext && entityShapeContext.getEntity() instanceof PlayerEntity) {
            return this.getShape(false);
        }
        return this.getShape(true);
    }

    private VoxelShape getShape(boolean filled) {
        if (this.player) {
            filled = !filled;
        }
        if (filled) {
            return VoxelShapes.fullCube();
        } else {
            return VoxelShapes.empty();
        }
    }

    @Override
    public boolean isTransparent(BlockState state, BlockView world, BlockPos pos) {
        return true;
    }

    @Override
    public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
        return 1.0f;
    }
    
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        if (VisibleBarriersAccess.areBarriersEnabled()) return BlockRenderType.MODEL;
        return BlockRenderType.INVISIBLE;
    }
}