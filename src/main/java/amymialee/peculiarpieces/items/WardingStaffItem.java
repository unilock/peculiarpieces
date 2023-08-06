package amymialee.peculiarpieces.items;

import amymialee.peculiarpieces.component.PeculiarComponentInitializer;
import amymialee.peculiarpieces.component.WardingComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Optional;

public class WardingStaffItem extends Item {
    public WardingStaffItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        if (context.getPlayer() != null && context.getPlayer().isCreative()) {
            var world = context.getWorld();
            var pos = context.getBlockPos();
            var component = PeculiarComponentInitializer.WARDING.maybeGet(world.getChunk(pos));
            if (component.isPresent()) {
                var wardingComponent = component.get();
                wardingComponent.setWard(pos, !wardingComponent.getWard(pos));
            }
            return ActionResult.success(world.isClient);
        }
        return super.useOnBlock(context);
    }
}