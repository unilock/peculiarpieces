package amymialee.peculiarpieces.blocks;

import amymialee.peculiarpieces.VisibleBarriersAccess;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.EntityShapeContext;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameMode;

public class GameModeBarrierBlock extends Block {
    private final GameMode gameMode;

    public GameModeBarrierBlock(GameMode gameMode, FabricBlockSettings settings) {
        super(settings);
        this.gameMode = gameMode;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context.isHolding(this.asItem()) || VisibleBarriersAccess.areBarriersEnabled()) {
            return VoxelShapes.fullCube();
        }
        if (context instanceof EntityShapeContext entityShapeContext && entityShapeContext.getEntity() instanceof PlayerEntity player) {
            if (player instanceof ServerPlayerEntity playerEntity && playerEntity.interactionManager.getGameMode() == this.gameMode) {
                return VoxelShapes.fullCube();
            } else if (player.getWorld().isClient() && player instanceof ClientPlayerEntity clientPlayerEntity) {
                PlayerListEntry playerListEntry = clientPlayerEntity.networkHandler.getPlayerListEntry(clientPlayerEntity.getUuid());
                if (playerListEntry != null && playerListEntry.getGameMode() == this.gameMode) {
                    return VoxelShapes.fullCube();
                }
            }
        }
        return VoxelShapes.empty();
    }

    @Override
    public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        if (context instanceof EntityShapeContext entityShapeContext && entityShapeContext.getEntity() instanceof PlayerEntity player) {
            if (player instanceof ServerPlayerEntity playerEntity && playerEntity.interactionManager.getGameMode() == this.gameMode) {
                return VoxelShapes.fullCube();
            } else if (player.getWorld().isClient() && player instanceof ClientPlayerEntity clientPlayerEntity) {
                PlayerListEntry playerListEntry = clientPlayerEntity.networkHandler.getPlayerListEntry(clientPlayerEntity.getUuid());
                if (playerListEntry != null && playerListEntry.getGameMode() == this.gameMode) {
                    return VoxelShapes.fullCube();
                }
            }
        }
        return VoxelShapes.empty();
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