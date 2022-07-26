package amymialee.peculiarpieces.client;

import amymialee.peculiarpieces.blockentities.FishTankBlockEntity;
import amymialee.peculiarpieces.blocks.FishTankBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3f;

public class FishTankBlockEntityRenderer implements BlockEntityRenderer<FishTankBlockEntity> {
    private ItemStack cachedStack;

    @Override
    public void render(FishTankBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        if (blockEntity.getCachedState().getBlock() instanceof FishTankBlock) {
            ItemStack newStack = blockEntity.getStack(0);
            if (newStack != cachedStack) {
                cachedStack = newStack;
            }
        }
        if (cachedStack != null && blockEntity.getWorld() != null) {
            double offset = Math.sin((blockEntity.getWorld().getTime() + tickDelta) / 16.0) / 16.0;
            matrices.translate(0.5, 0.3 + offset, 0.5);
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion((blockEntity.getWorld().getTime() + tickDelta) * 4));
            int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());
            MinecraftClient.getInstance().getItemRenderer().renderItem(cachedStack, ModelTransformation.Mode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, 0);
        }
        matrices.pop();
    }
}