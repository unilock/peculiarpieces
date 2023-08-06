package amymialee.peculiarpieces.items;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;

public class EternalDoughnutItem extends Item {
	public EternalDoughnutItem(FabricItemSettings settings) {
		super(settings);
	}

	public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
		var itemStack = user.getStackInHand(hand);
		if (user.canConsume(true)) {
			user.setCurrentHand(hand);
			return TypedActionResult.consume(itemStack);
		} else {
			return TypedActionResult.fail(itemStack);
		}
	}

	public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
		if (user instanceof PlayerEntity player) {
			player.getHungerManager().add(4, 4);
		}
		world.playSound(null, user.getX(), user.getY(), user.getZ(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, 1.0F, 1.0F + (world.random.nextFloat() - world.random.nextFloat()) * 0.4F);
		user.emitGameEvent(GameEvent.EAT);
		return stack;
	}

	public UseAction getUseAction(ItemStack stack) {
		return UseAction.EAT;
	}

	public int getMaxUseTime(ItemStack stack) {
		return 12;
	}

	public boolean isFood() {
		return false;
	}
}
