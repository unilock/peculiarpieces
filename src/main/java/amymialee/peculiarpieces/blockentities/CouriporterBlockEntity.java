package amymialee.peculiarpieces.blockentities;

import amymialee.peculiarpieces.registry.PeculiarBlocks;
import amymialee.peculiarpieces.screens.WarpScreenHandler;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;

public class CouriporterBlockEntity extends LootableContainerBlockEntity {
    private DefaultedList<ItemStack> inventory;

    public CouriporterBlockEntity(BlockPos pos, BlockState state) {
        super(PeculiarBlocks.COURIPORTER_BLOCK_ENTITY, pos, state);
        this.inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory);
    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
    }

    protected Text getContainerName() {
        return Text.translatable("peculiarpieces.container.couriporter");
    }

    protected DefaultedList<ItemStack> getInvStackList() {
        return this.inventory;
    }

    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.inventory = list;
    }

    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new WarpScreenHandler(syncId, playerInventory, this);
    }

    public DefaultedList<ItemStack> getInventory() {
        return this.inventory;
    }

    @Override
    public int size() {
        return this.inventory.size();
    }
}