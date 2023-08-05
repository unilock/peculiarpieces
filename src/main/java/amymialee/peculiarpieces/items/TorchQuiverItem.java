package amymialee.peculiarpieces.items;

import amymialee.peculiarpieces.CustomCreativeItems;
import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.mixin.ItemUsageContextAccessor;
import amymialee.peculiarpieces.util.PeculiarHelper;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.item.RangedWeaponItem;
import net.minecraft.item.ItemGroup.Entries;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class TorchQuiverItem extends RangedWeaponItem implements CustomCreativeItems {
    public static final Predicate<ItemStack> TORCHES = stack -> stack.isIn(PeculiarPieces.TORCHES) && stack.getNbt() == null;
    private static final Item[] torches = {Items.TORCH, Items.SOUL_TORCH};
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
    protected static final UUID REACH_MODIFIER_ID = UUID.fromString("3CE62193-A8F6-4137-8109-C4516ADCED06");

    public TorchQuiverItem(Settings settings) {
        super(settings);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(ReachEntityAttributes.REACH, new EntityAttributeModifier(REACH_MODIFIER_ID, "Reach modifier", 16, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (user.isSneaking()) {
            NbtCompound compound = stack.getOrCreateNbt();
            stack.getOrCreateNbt().putInt("pp:setting", PeculiarHelper.clampLoop(0, torches.length - 1, compound.getInt("pp:setting") + 1));
            user.getItemCooldownManager().set(this, 2);
        }
        if (stack.getOrCreateNbt().getBoolean("Unbreakable")) {
            stack.getOrCreateNbt().remove("Unbreakable");
        }
        return TypedActionResult.fail(stack);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemStack quiver = context.getStack();
        if (context.getPlayer() != null && (quiver.getDamage() < getMaxDamage() || context.getPlayer().isCreative())) {
            int setting = quiver.getOrCreateNbt().getInt("pp:setting");
            if (setting < torches.length) {
                Item item = torches[setting];
                if (item instanceof BlockItem blockItem) {
                    ActionResult result = blockItem.place(new ItemPlacementContext(context.getPlayer(), context.getHand(), new ItemStack(item), ((ItemUsageContextAccessor) context).getHit()));
                    if (result.isAccepted() && !context.getPlayer().isCreative()) {
                        quiver.setDamage(quiver.getDamage() + 1);
                    }
                    return result;
                }
            }
        }
        return super.useOnBlock(context);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (selected && stack.getDamage() > 0 && entity instanceof PlayerEntity player && !player.isCreative()) {
            ItemStack torches = player.getProjectileType(stack);
            if (!torches.isEmpty()) {
                stack.setDamage(stack.getDamage() - 1);
                torches.decrement(1);
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return stack.getDamage() > 0;
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return switch (stack.getOrCreateNbt().getInt("pp:setting")) {
            case 1 -> 3336430;
            default -> 16766976;
        };
    }
    
    @Override
    public void appendStacks(Entries entries) {
        for (int filled = 0; filled <= 1; filled++) {
            for (int setting = 0; setting < torches.length; setting++) {
                ItemStack stack = new ItemStack(this);
                if (filled == 0) {
                    stack.setDamage(512);
                }
                stack.getOrCreateNbt().putInt("pp:setting", setting);
                entries.add(stack);
            }
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (!context.isAdvanced()) {
            tooltip.add(Text.translatable("item.durability", stack.getMaxDamage() - stack.getDamage(), stack.getMaxDamage()));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        if (slot == EquipmentSlot.MAINHAND) {
            return this.attributeModifiers;
        }
        return super.getAttributeModifiers(slot);
    }

    @Override
    public Predicate<ItemStack> getProjectiles() {
        return TORCHES;
    }

    @Override
    public int getRange() {
        return 0;
    }
}