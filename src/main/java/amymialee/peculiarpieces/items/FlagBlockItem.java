package amymialee.peculiarpieces.items;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.blockentities.FlagBlockEntity;
import amymialee.peculiarpieces.blocks.FlagBlock;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import org.apache.commons.lang3.Validate;

public class FlagBlockItem extends BlockItem {
    public FlagBlockItem(Block block, FabricItemSettings settings) {
        super(block, settings);
        Validate.isInstanceOf(FlagBlock.class, block);
    }

    @Override
    public Text getName(ItemStack stack) {
        NbtCompound nbtCompound = BlockItem.getBlockEntityNbt(stack);
        if (nbtCompound != null && nbtCompound.contains(FlagBlockEntity.TEXTURE_KEY, 8)) {
            return Text.translatable("block.%s.%s_flag".formatted(PeculiarPieces.MOD_ID, nbtCompound.getString(FlagBlockEntity.TEXTURE_KEY).toLowerCase()));
        }
        return super.getName(stack);
    }
}