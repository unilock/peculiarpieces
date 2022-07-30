package amymialee.peculiarpieces.items;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.mixin.ItemUsageContextAccessor;
import amymialee.peculiarpieces.util.PeculiarHelper;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.jamieswhiteshirt.reachentityattributes.ReachEntityAttributes;
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
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

import java.util.UUID;
import java.util.function.Predicate;

public class TorchQuiverItem extends RangedWeaponItem {
    public static final Predicate<ItemStack> TORCHES = stack -> stack.isIn(PeculiarPieces.TORCHES) && stack.getNbt() == null;
    private static final Item[] torches = {Items.TORCH, Items.SOUL_TORCH};
    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;
    protected static final UUID REACH_MODIFIER_ID = UUID.fromString("3CE62193-A8F6-4137-8109-C4516ADCED06");

    public TorchQuiverItem(Settings settings) {
        super(settings);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(ReachEntityAttributes.REACH, new EntityAttributeModifier(REACH_MODIFIER_ID, "Reach modifier", 8, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);
        if (user.isSneaking()) {
            NbtCompound compound = stack.getOrCreateNbt();
            stack.getOrCreateNbt().putInt("pp:setting", PeculiarHelper.clampLoop(0, 1, compound.getInt("pp:setting") + 1));
            user.getItemCooldownManager().set(this, 2);
        }
        return TypedActionResult.fail(stack);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        ItemStack quiver = context.getStack();
        if (context.getPlayer() != null && (quiver.getDamage() < getMaxDamage() || context.getPlayer().isCreative())) {
            Item item = torches[quiver.getOrCreateNbt().getInt("pp:setting")];
            if (item instanceof BlockItem blockItem) {
                ActionResult result = blockItem.place(new ItemPlacementContext(context.getPlayer(), context.getHand(), new ItemStack(item), ((ItemUsageContextAccessor) context).getHit()));
                if (result.isAccepted() && !context.getPlayer().isCreative()) {
                    quiver.setDamage(quiver.getDamage() + 1);
                }
                return result;
            }
        }
        return super.useOnBlock(context);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (selected && stack.getDamage() > 0 && entity instanceof PlayerEntity player && !player.isCreative()) {
            ItemStack torches = player.getArrowType(stack);
            if (!torches.isEmpty()) {
                stack.setDamage(stack.getDamage() - 1);
                torches.decrement(1);
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return switch (stack.getOrCreateNbt().getInt("pp:setting")) {
            case 1 -> 3336430;
            default -> 16766976;
        };
    }

    @Override
    public void appendStacks(ItemGroup group, DefaultedList<ItemStack> stacks) {
        if (this.isIn(group)) {
            for (int filled = 0; filled <= 1; filled++) {
                for (int setting = 0; setting <= 1; setting++) {
                    ItemStack stack = new ItemStack(this);
                    if (filled == 0) {
                        stack.setDamage(512);
                    }
                    stack.getOrCreateNbt().putInt("pp:setting", setting);
                    stacks.add(stack);
                }
            }
        }
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