package amymialee.peculiarpieces.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.World;

public class SlimeItem extends Item {
    public SlimeItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        SlimeEntity slime = EntityType.SLIME.create(context.getWorld());
        if (slime != null) {
            slime.setSize(0, true);
            slime.setPosition(Vec3d.ofBottomCenter(context.getBlockPos().add(context.getSide().getVector())));
            if (context.getWorld().spawnEntity(slime)) {
                context.getStack().decrement(1);
            }
        }
        return super.useOnBlock(context);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity != null && world instanceof StructureWorldAccess access) {
            ChunkPos pos = entity.getChunkPos();
            if (ChunkRandom.getSlimeRandom(pos.x, pos.z, access.getSeed(), 987234911L).nextInt(10) == 0) {
                stack.getOrCreateNbt().putBoolean("pp:chunked", true);
            } else if (stack.getNbt() != null) {
                stack.getNbt().remove("pp:chunked");
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }
}