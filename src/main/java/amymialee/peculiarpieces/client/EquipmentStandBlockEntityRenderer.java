package amymialee.peculiarpieces.client;

import amymialee.peculiarpieces.blockentities.EquipmentStandBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

public class EquipmentStandBlockEntityRenderer implements BlockEntityRenderer<EquipmentStandBlockEntity> {
    @Override
    public void render(EquipmentStandBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        LivingEntity cachedEntity = blockEntity.getCachedEntity();
        if (cachedEntity != null) {
            matrices.translate(0.5f, 0, 0.5f);
            MinecraftClient.getInstance().getEntityRenderDispatcher().render(cachedEntity, 0f, 0.2f, 0f, 0f, tickDelta, matrices, vertexConsumers, light);
        }
        matrices.pop();
    }
}