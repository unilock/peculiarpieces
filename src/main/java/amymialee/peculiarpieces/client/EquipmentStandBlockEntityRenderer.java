package amymialee.peculiarpieces.client;

import amymialee.peculiarpieces.blockentities.EquipmentStandBlockEntity;
import amymialee.peculiarpieces.blocks.EquipmentStandBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.RotationAxis;

public class EquipmentStandBlockEntityRenderer implements BlockEntityRenderer<EquipmentStandBlockEntity> {
    @Override
    public void render(EquipmentStandBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        LivingEntity cachedEntity = blockEntity.getCachedEntity();
        if (cachedEntity != null) {
            matrices.translate(0.5f, 0, 0.5f);
            if (blockEntity.getCachedState().get(EquipmentStandBlock.POWERED)) {
                cachedEntity.setHeadYaw(blockEntity.getPlayerYaw());
            } else {
                cachedEntity.setHeadYaw(0);
            }
            float h = (float) (-blockEntity.getCachedState().get(EquipmentStandBlock.ROTATION) * 360) / 16.0f;
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(h));
            MinecraftClient.getInstance().getEntityRenderDispatcher().render(cachedEntity, 0f, 0.2f - 0.01625f, 0f, 0f, 1f, matrices, vertexConsumers, light);
        }
        matrices.pop();
    }
}