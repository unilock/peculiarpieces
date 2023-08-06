package amymialee.peculiarpieces.blocks;

import amymialee.peculiarpieces.VisibleBarriersAccess;
import amymialee.peculiarpieces.util.ExtraPlayerDataWrapper;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.GameMode;
import net.minecraft.world.World;

public class GameModeSetterBlock extends AbstractStructureVoidBlock {
    private final GameMode gameMode;

    public GameModeSetterBlock(GameMode gameMode, FabricBlockSettings settings) {
        super(settings);
        this.gameMode = gameMode;
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        super.onEntityCollision(state, world, pos, entity);
        if (!world.isClient()) {
            if (entity instanceof ServerPlayerEntity player) {
                if (player instanceof ExtraPlayerDataWrapper wrapper) {
                    if (this.gameMode == wrapper.getStoredGameMode()) {
                        return;
                    }
                    var playerMode = player.interactionManager.getGameMode();
                    if (playerMode != this.gameMode && playerMode != GameMode.SPECTATOR && playerMode != GameMode.CREATIVE) {
                        if (wrapper.getGameModeDuration() == 0) {
                            wrapper.setStoredGameMode(playerMode);
                        }
                        player.changeGameMode(this.gameMode);
                        wrapper.setGameModeDuration(3);
                        return;
                    }
                    if (wrapper.getGameModeDuration() > 0) {
                        wrapper.setGameModeDuration(3);
                    }
                }
            }
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
    	if (VisibleBarriersAccess.isVisibilityEnabled()) return BlockRenderType.MODEL;
        return BlockRenderType.INVISIBLE;
    }
    
}