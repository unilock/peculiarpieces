package amymialee.peculiarpieces;

import java.util.ArrayList;

import amymialee.peculiarpieces.registry.PeculiarBlocks;
import amymialee.peculiarpieces.registry.PeculiarItems;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemGroup.DisplayContext;
import net.minecraft.item.ItemGroup.Entries;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;

public class PeculiarItemGroups {
    
    private static void build(ArrayList<Item> items, Entries entries) {
        for (Item i : items) {
            if (i instanceof CustomCreativeItems cci) {
                cci.appendStacks(entries);
            } else if (i instanceof BlockItem bi && bi.getBlock() instanceof CustomCreativeItems cci) {
                cci.appendStacks(entries);
            } else {
                entries.add(i);
            }
        }
    }

    public static void buildPieces(DisplayContext ctx, Entries entries) {
        build(PeculiarItems.MOD_ITEMS, entries);
        entries.add(PeculiarBlocks.WHITE_ELEVATOR);
        entries.add(PeculiarBlocks.LIGHT_GRAY_ELEVATOR);
        entries.add(PeculiarBlocks.GRAY_ELEVATOR);
        entries.add(PeculiarBlocks.BLACK_ELEVATOR);
        entries.add(PeculiarBlocks.BROWN_ELEVATOR);
        entries.add(PeculiarBlocks.RED_ELEVATOR);
        entries.add(PeculiarBlocks.ORANGE_ELEVATOR);
        entries.add(PeculiarBlocks.YELLOW_ELEVATOR);
        entries.add(PeculiarBlocks.LIME_ELEVATOR);
        entries.add(PeculiarBlocks.GREEN_ELEVATOR);
        entries.add(PeculiarBlocks.CYAN_ELEVATOR);
        entries.add(PeculiarBlocks.LIGHT_BLUE_ELEVATOR);
        entries.add(PeculiarBlocks.BLUE_ELEVATOR);
        entries.add(PeculiarBlocks.PURPLE_ELEVATOR);
        entries.add(PeculiarBlocks.MAGENTA_ELEVATOR);
        entries.add(PeculiarBlocks.PINK_ELEVATOR);
        entries.add(PeculiarBlocks.WHITE_ROTATING_ELEVATOR);
        entries.add(PeculiarBlocks.LIGHT_GRAY_ROTATING_ELEVATOR);
        entries.add(PeculiarBlocks.GRAY_ROTATING_ELEVATOR);
        entries.add(PeculiarBlocks.BLACK_ROTATING_ELEVATOR);
        entries.add(PeculiarBlocks.BROWN_ROTATING_ELEVATOR);
        entries.add(PeculiarBlocks.RED_ROTATING_ELEVATOR);
        entries.add(PeculiarBlocks.ORANGE_ROTATING_ELEVATOR);
        entries.add(PeculiarBlocks.YELLOW_ROTATING_ELEVATOR);
        entries.add(PeculiarBlocks.LIME_ROTATING_ELEVATOR);
        entries.add(PeculiarBlocks.GREEN_ROTATING_ELEVATOR);
        entries.add(PeculiarBlocks.CYAN_ROTATING_ELEVATOR);
        entries.add(PeculiarBlocks.LIGHT_BLUE_ROTATING_ELEVATOR);
        entries.add(PeculiarBlocks.BLUE_ROTATING_ELEVATOR);
        entries.add(PeculiarBlocks.PURPLE_ROTATING_ELEVATOR);
        entries.add(PeculiarBlocks.MAGENTA_ROTATING_ELEVATOR);
        entries.add(PeculiarBlocks.PINK_ROTATING_ELEVATOR);
    }

    public static void buildCreative(DisplayContext ctx, Entries entries) {
        build(PeculiarItems.CREATIVE_ITEMS, entries);
    }

    public static void buildPotion(DisplayContext ctx, Entries entries) {
        generatePotionEntries(entries, ctx.lookup().getWrapperOrThrow(RegistryKeys.POTION), PeculiarItems.HIDDEN_POTION);
    }
    

    private static void generatePotionEntries(ItemGroup.Entries entries, RegistryWrapper<Potion> potions, Item potion) {
        potions.streamEntries()
                .filter(en -> !en.matchesKey(Potions.EMPTY_KEY))
                .map(en -> PotionUtil.setPotion(new ItemStack(potion), (Potion) en.value()))
                .forEach(stack -> entries.add(stack));
    }

}
