package amymialee.peculiarpieces.client;

import amymialee.peculiarpieces.blockentities.RedstoneTriggerBlockEntity;
import amymialee.peculiarpieces.blocks.RedstoneTriggerBlock;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.RotationAxis;

public class RedstoneTriggerBlockEntityRenderer implements BlockEntityRenderer<RedstoneTriggerBlockEntity> {
    @Override
    public void render(RedstoneTriggerBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        if (blockEntity.getWorld() != null) {
            double offset = Math.sin((blockEntity.getWorld().getTime() + tickDelta) / 16.0) / 32.0;
            matrices.translate(0.5, 0.4 + offset, 0.5);
            BlockState state = blockEntity.getCachedState();
            if (state.getBlock() instanceof RedstoneTriggerBlock) {
                float h = blockEntity.bookRotation - blockEntity.lastBookRotation;
                while (h >= (float)Math.PI) {
                    h -= (float)Math.PI * 2;
                }
                while (h < (float)(-Math.PI)) {
                    h += (float)Math.PI * 2;
                }
                float k = blockEntity.lastBookRotation + h * tickDelta;
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-k));
                matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(90));
            }
            int lightAbove = WorldRenderer.getLightmapCoordinates(blockEntity.getWorld(), blockEntity.getPos().up());
            MinecraftClient.getInstance().getItemRenderer().renderItem(Items.AMETHYST_SHARD.getDefaultStack(), ModelTransformationMode.GROUND, lightAbove, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, blockEntity.getWorld(), 0);
        }
        matrices.pop();
    }
}