package amymialee.peculiarpieces.client;

import amymialee.peculiarpieces.blockentities.EquipmentStandBlockEntity;
import amymialee.peculiarpieces.blocks.FlagBlock;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3f;

public class EquipmentStandBlockEntityRenderer implements BlockEntityRenderer<EquipmentStandBlockEntity> {
    @Override
    public void render(EquipmentStandBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        LivingEntity cachedEntity = blockEntity.getCachedEntity();
        if (cachedEntity != null) {
            cachedEntity.setInvisible(true);
            matrices.translate(0.5f, 0, 0.5f);
            float h = (float) (-blockEntity.getCachedState().get(FlagBlock.ROTATION) * 360) / 16.0f;
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(h));
            MinecraftClient.getInstance().getEntityRenderDispatcher().render(cachedEntity, 0f, 0.2f - 0.01625f, 0f, 0f, 1f, matrices, vertexConsumers, light);
        }
        matrices.pop();
    }
}