package amymialee.peculiarpieces.client;

import amymialee.peculiarpieces.blockentities.FishTankBlockEntity;
import amymialee.peculiarpieces.mixin.EntityAccessor;
import amymialee.peculiarpieces.mixin.EntityBucketItemAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.TropicalFishEntity;
import static net.minecraft.entity.passive.TropicalFishEntity.BUCKET_VARIANT_TAG_KEY;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3f;
import net.minecraft.world.GameRules;

public class FishTankBlockEntityRenderer implements BlockEntityRenderer<FishTankBlockEntity> {
    private FishEntity cachedEntity;
    private ItemStack cachedStack;
    private int age;

    @Override
    public void render(FishTankBlockEntity blockEntity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        if (blockEntity.getWorld() != null) {
            ItemStack stack = blockEntity.getStack(0);
            if (stack != null) {
                if (stack != cachedStack) {
                    cachedEntity = null;
                    cachedStack = stack;
                }
                if (cachedEntity == null) {
                    if (stack.getItem() instanceof EntityBucketItem bucket) {
                        Entity entity = ((EntityBucketItemAccessor) bucket).getEntityType().create(blockEntity.getWorld());
                        if (entity instanceof FishEntity fish) {
                            cachedEntity = fish;
                        }
                    } else {
                        matrices.pop();
                        return;
                    }
                    cachedEntity.setPosition(Vec3d.of(blockEntity.getPos()));
                    ((EntityAccessor) cachedEntity).setTouchingWater(true);
                    cachedEntity.setFromBucket(true);
                    if (cachedEntity instanceof TropicalFishEntity tropicalFish) {
                        tropicalFish.setVariant(stack.getOrCreateNbt().getInt(BUCKET_VARIANT_TAG_KEY));
                    }
                }
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
                    matrices.multiply(Quaternion.fromEulerXyzDegrees(new Vec3f(0, -cachedStack.getOrCreateNbt().getFloat("pp:yaw"), 0)));
                }
                MinecraftClient.getInstance().getEntityRenderDispatcher().render(cachedEntity, 0f, 0.2f, 0f, 0f, tickDelta, matrices, vertexConsumers, light);
            } else {
                cachedEntity = null;
            }
        }
        matrices.pop();
    }
}