package amymialee.peculiarpieces.blockentities;

import amymialee.peculiarpieces.blocks.FishTankBlock;
import amymialee.peculiarpieces.mixin.EntityAccessor;
import amymialee.peculiarpieces.mixin.EntityBucketItemAccessor;
import amymialee.peculiarpieces.registry.PeculiarBlocks;
import amymialee.peculiarpieces.screens.FishTankScreenHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.TropicalFishEntity;
import static net.minecraft.entity.passive.TropicalFishEntity.BUCKET_VARIANT_TAG_KEY;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.EntityBucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class FishTankBlockEntity extends LockableContainerBlockEntity {
    private DefaultedList<ItemStack> inventory;
    private FishEntity cachedEntity;
    private ItemStack cachedStack;
    private float yaw;

    public FishTankBlockEntity(BlockPos pos, BlockState state) {
        super(PeculiarBlocks.FISH_TANK_BLOCK_ENTITY, pos, state);
        this.inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
    }

    public FishEntity getCachedEntity() {
        if (getStack(0) != cachedStack) {
            cachedEntity = null;
            cachedStack = getStack(0);
        }
        if (cachedEntity == null) {
            if (cachedStack.getItem() instanceof EntityBucketItem bucket) {
                Entity entity = ((EntityBucketItemAccessor) bucket).getEntityType().create(getWorld());
                if (entity instanceof FishEntity fish) {
                    cachedEntity = fish;
                }
            } else {
                return null;
            }
            cachedEntity.setPosition(Vec3d.of(getPos()));
            ((EntityAccessor) cachedEntity).setTouchingWater(true);
            cachedEntity.setFromBucket(true);
            if (cachedEntity instanceof TropicalFishEntity tropicalFish) {
                tropicalFish.setVariant(TropicalFishEntity.Variety.fromId(cachedStack.getOrCreateNbt().getInt(BUCKET_VARIANT_TAG_KEY)));
            }
        }
        return cachedEntity;
    }

    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory);
        this.yaw = nbt.getFloat("pp:yaw");
    }

    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        nbt.putFloat("pp:yaw", yaw);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        NbtCompound nbtCompound = super.toInitialChunkDataNbt();
        writeNbt(nbtCompound);
        return nbtCompound;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    protected Text getContainerName() {
        return Text.translatable("peculiarpieces.container.fish_tank");
    }

    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new FishTankScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : this.inventory) {
            if (itemStack.isEmpty()) continue;
            return false;
        }
        return true;
    }

    @Override
    public ItemStack getStack(int slot) {
        if (slot >= 0 && slot < this.inventory.size()) {
            return this.inventory.get(slot);
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStack(int slot, int amount) {
        ItemStack stack = Inventories.splitStack(this.inventory, slot, amount);
        updateState();
        return stack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        ItemStack stack = Inventories.removeStack(this.inventory, slot);
        updateState();
        return stack;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
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

    public float getYaw() {
        return yaw;
    }

    public void setYaw(float yaw) {
        this.yaw = yaw;
    }

    public void updateState() {
        if (world != null && !world.isClient()) {
            boolean present = !getStack(0).isEmpty();
            BlockState oldState = world.getBlockState(pos);
            if (oldState.get(FishTankBlock.FILLED) != present) {
                world.setBlockState(pos, world.getBlockState(pos).with(FishTankBlock.FILLED, present));
            }
            world.updateListeners(pos, oldState, world.getBlockState(pos), Block.NOTIFY_LISTENERS);
        }
    }
}