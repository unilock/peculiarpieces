package amymialee.peculiarpieces.client;

import amymialee.peculiarpieces.blockentities.FishTankBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.GameRules;

public class FishTankBlockEntityRenderer implements BlockEntityRenderer<FishTankBlockEntity> {
    @Override
    public void render(FishTankBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        if (blockEntity.getWorld() != null) {
            ItemStack stack = blockEntity.getStack(0);
            if (stack != null) {
                FishEntity cachedEntity = blockEntity.getCachedEntity();
                if (cachedEntity != null) {
                    if (blockEntity.getWorld().getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)) {
                        cachedEntity.age = (int) blockEntity.getWorld().getTimeOfDay();
                    } else {
                        PlayerEntity player = MinecraftClient.getInstance().player;
                        if (player != null) {
                            cachedEntity.age = player.age;
                        }
                    }
                    matrices.translate(0.5f, 0, 0.5f);
                    if (blockEntity.getWorld().getBlockEntity(blockEntity.getPos()) instanceof FishTankBlockEntity) {
                        matrices.multiply(Quaternion.fromEulerXyzDegrees(new Vec3f(0, -blockEntity.getYaw(), 0)));
                    }
                    MinecraftClient.getInstance().getEntityRenderDispatcher().render(cachedEntity, 0f, 0.2f, 0f, 0f, tickDelta, matrices, vertexConsumers, light);
                }
            }
        }
        matrices.pop();
    }
}