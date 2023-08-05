package amymialee.peculiarpieces.client;

import amymialee.peculiarpieces.blockentities.CreativeBarrelBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;

public class CreativeBarrelBlockEntityRenderer implements BlockEntityRenderer<CreativeBarrelBlockEntity> {
    private ItemStack cachedStack;

    @Override
    public void render(CreativeBarrelBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        ItemStack newStack = blockEntity.getStack(0);
        if (newStack != cachedStack) {
            cachedStack = newStack;
        }
        if (cachedStack != null && blockEntity.getWorld() != null) {
            matrices.translate(0.5, cachedStack.getItem() instanceof BlockItem ? 0.3 : 0.35, 0.5);
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((blockEntity.getWorld().getTime() + tickDelta) * 4));
            MinecraftClient.getInstance().getItemRenderer().renderItem(cachedStack, ModelTransformationMode.GROUND, light, OverlayTexture.DEFAULT_UV, matrices, vertexConsumers, blockEntity.getWorld(), 0);
        }
        matrices.pop();
    }
}