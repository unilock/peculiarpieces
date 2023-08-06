package amymialee.peculiarpieces.items;

import amymialee.peculiarpieces.util.LockableBlockEntityWrapper;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.inventory.ContainerLock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LockingKeyItem extends Item {
    public LockingKeyItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        var world = context.getWorld();
        var pos = context.getBlockPos();
        var blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof LockableBlockEntityWrapper lockable) {
            if (!lockable.getLock().canOpen(context.getStack())) {
                lockable.setLock(ContainerLock.EMPTY);
            } else {
                lockable.setLock(new ContainerLock(String.valueOf(context.hashCode())));
            }
        }
        return ActionResult.SUCCESS;
    }
}