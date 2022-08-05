package amymialee.peculiarpieces.blockentities;

import amymialee.peculiarpieces.registry.PeculiarBlocks;
import amymialee.peculiarpieces.screens.CreativeBarrelScreenHandler;
import net.minecraft.block.BarrelBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.block.entity.ViewerCountManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class CreativeBarrelBlockEntity extends LootableContainerBlockEntity {
    private final ViewerCountManager stateManager = new CreativeBarrelViewerManager();
    private DefaultedList<ItemStack> inventory;

    public CreativeBarrelBlockEntity(BlockPos pos, BlockState state) {
        super(PeculiarBlocks.CREATIVE_BARREL_BLOCK_ENTITY, pos, state);
        this.inventory = DefaultedList.ofSize(size(), ItemStack.EMPTY);
    }

    @Override
    public ItemStack getStack(int slot) {
        return super.getStack(slot).copy();
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack stack = super.getStack(slot).copy();
        stack.setCount(amount);
        updateState();
        return stack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        return super.getStack(slot).copy();
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (stack.isEmpty()) return;
        if (slot >= 0 && slot < this.inventory.size()) {
            this.inventory.set(slot, stack);
        }
        updateState();
    }

    @Override
    public void clear() {
        this.inventory.clear();
        updateState();
    }

    public void updateState() {
        if (world != null && !world.isClient()) {
            BlockState state = world.getBlockState(pos);
            world.updateListeners(pos, state, state, Block.NOTIFY_LISTENERS);
        }
    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbtCompound = super.toInitialChunkDataNbt();
        Inventories.writeNbt(nbtCompound, this.inventory, true);
        return nbtCompound;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    public int size() {
        return 1;
    }

    protected DefaultedList<ItemStack> getInvStackList() {
        return this.inventory;
    }

    protected void setInvStackList(DefaultedList<ItemStack> list) {
        this.inventory = list;
    }

    protected Text getContainerName() {
        return Text.translatable("peculiarpieces.container.creative_barrel");
    }

    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new CreativeBarrelScreenHandler(syncId, playerInventory, this);
    }

    public void onOpen(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.openContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    public void onClose(PlayerEntity player) {
        if (!this.removed && !player.isSpectator()) {
            this.stateManager.closeContainer(player, this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    public void tick() {
        if (!this.removed) {
            this.stateManager.updateViewerCount(this.getWorld(), this.getPos(), this.getCachedState());
        }
    }

    void setOpen(BlockState state, boolean open) {
        if (this.world != null) {
            this.world.setBlockState(this.getPos(), state.with(BarrelBlock.OPEN, open), 3);
        }
    }

    void playSound(BlockState state, SoundEvent soundEvent) {
        Vec3i vec3i = state.get(BarrelBlock.FACING).getVector();
        double d = (double)this.pos.getX() + 0.5 + (double)vec3i.getX() / 2.0;
        double e = (double)this.pos.getY() + 0.5 + (double)vec3i.getY() / 2.0;
        double f = (double)this.pos.getZ() + 0.5 + (double)vec3i.getZ() / 2.0;
        if (this.world != null) {
            this.world.playSound(null, d, e, f, soundEvent, SoundCategory.BLOCKS, 0.5F, this.world.random.nextFloat() * 0.1F + 0.9F);
        }
    }

    private class CreativeBarrelViewerManager extends ViewerCountManager {
        protected void onContainerOpen(World world, BlockPos pos, BlockState state) {
            CreativeBarrelBlockEntity.this.playSound(state, SoundEvents.BLOCK_BARREL_OPEN);
            CreativeBarrelBlockEntity.this.setOpen(state, true);
        }

        protected void onContainerClose(World world, BlockPos pos, BlockState state) {
            CreativeBarrelBlockEntity.this.playSound(state, SoundEvents.BLOCK_BARREL_CLOSE);
            CreativeBarrelBlockEntity.this.setOpen(state, false);
        }

        protected void onViewerCountUpdate(World world, BlockPos pos, BlockState state, int oldViewerCount, int newViewerCount) {}

        protected boolean isPlayerViewing(PlayerEntity player) {
            if (player.currentScreenHandler instanceof GenericContainerScreenHandler) {
                Inventory inventory = ((GenericContainerScreenHandler)player.currentScreenHandler).getInventory();
                return inventory == CreativeBarrelBlockEntity.this;
            } else {
                return false;
            }
        }
    }
}