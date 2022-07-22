package amymialee.peculiarpieces.items;

import amymialee.peculiarpieces.util.RedstoneInstance;
import amymialee.peculiarpieces.util.RedstoneManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.util.ActionResult;

public class RedstoneActivatorItem extends Item {
    public RedstoneActivatorItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        RedstoneManager.addInstance(context.getWorld(), context.getBlockPos(), new RedstoneInstance());
        PlayerEntity player = context.getPlayer();
        if (player != null) {
            player.getItemCooldownManager().set(this, 2);
        }
        return ActionResult.SUCCESS;
    }
}