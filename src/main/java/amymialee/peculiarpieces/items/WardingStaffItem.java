package amymialee.peculiarpieces.items;

import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WardingStaffItem extends Item {
    public WardingStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getPlayer() != null && context.getPlayer().isCreative()) {
            World world = context.getWorld();
            BlockPos pos = context.getBlockPos();
            return ActionResult.success(world.isClient);
        }
        return super.useOnBlock(context);
    }
}