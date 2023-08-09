package amymialee.peculiarpieces.blockentities;

import amymialee.peculiarpieces.blocks.FlagBlock;
import amymialee.peculiarpieces.entity.EquipmentStandEntity;
import amymialee.peculiarpieces.registry.PeculiarBlocks;
import amymialee.peculiarpieces.registry.PeculiarEntities;
import amymialee.peculiarpieces.screens.EquipmentStandScreenHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

public class EquipmentStandBlockEntity extends LockableContainerBlockEntity {
    private DefaultedList<ItemStack> inventory;
    private EquipmentStandEntity cachedEntity;
    private float playerYaw = 0;

    public EquipmentStandBlockEntity(BlockPos pos, BlockState state) {
        super(PeculiarBlocks.EQUIPMENT_STAND_BLOCK_ENTITY, pos, state);
        this.inventory = DefaultedList.ofSize(7, ItemStack.EMPTY);
    }

    public void updatePlayerYaw() {
        if (this.world != null) {
            var playerEntity = this.world.getClosestPlayer((double) this.pos.getX() + 0.5, (double) this.pos.getY() + 0.5, (double) this.pos.getZ() + 0.5, 32.0, false);
            if (playerEntity != null) {
                var d = playerEntity.getX() - ((double) this.pos.getX() + 0.5);
                var e = playerEntity.getZ() - ((double) this.pos.getZ() + 0.5);
                this.playerYaw = (float) (Math.toDegrees(MathHelper.atan2(e, d)) - (this.getCachedState().get(FlagBlock.ROTATION) * 360) / 16) - 90;
            }
        }
    }

    public EquipmentStandEntity getCachedEntity() {
        if (this.cachedEntity == null) {
            this.cachedEntity = PeculiarEntities.EQUIPMENT_STAND_ENTITY.create(this.getWorld());
        }
        if (this.cachedEntity != null) {
            this.cachedEntity.setPosition(Vec3d.of(this.getPos()));
            for (var i = 0; i < EquipmentSlot.values().length; i++) {
                var slot = EquipmentSlot.values()[i];
                this.cachedEntity.equipStack(slot, this.getStack(i));
            }
            this.cachedEntity.equipGliderStack(this.getStack(6));
        }
        return this.cachedEntity;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        this.inventory = DefaultedList.ofSize(this.size(), ItemStack.EMPTY);
        Inventories.readNbt(nbt, this.inventory);
        this.playerYaw = nbt.getFloat("pp:playeryaw");
    }

    @Override
    protected void writeNbt(NbtCompound nbt) {
        super.writeNbt(nbt);
        Inventories.writeNbt(nbt, this.inventory);
        nbt.putFloat("pp:playeryaw", this.playerYaw);
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket() {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt() {
        var nbtCompound = super.toInitialChunkDataNbt();
        Inventories.writeNbt(nbtCompound, this.inventory, true);
        nbtCompound.putFloat("pp:playeryaw", this.playerYaw);
        return nbtCompound;
    }

    @Override
    public boolean canPlayerUse(PlayerEntity player) {
        return true;
    }

    @Override
    protected Text getContainerName() {
        return Text.translatable("peculiarpieces.container.equipment_stand");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory) {
        return new EquipmentStandScreenHandler(syncId, playerInventory, this);
    }

    @Override
    public int size() {
        return this.inventory.size();
    }

    @Override
    public boolean isEmpty() {
        for (var itemStack : this.inventory) {
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
        var stack = Inventories.splitStack(this.inventory, slot, amount);
        this.updateState();
        return stack;
    }

    @Override
    public ItemStack removeStack(int slot) {
        var stack = Inventories.removeStack(this.inventory, slot);
        this.updateState();
        return stack;
    }

    @Override
    public void setStack(int slot, ItemStack stack) {
        if (slot >= 0 && slot < this.inventory.size()) {
            this.inventory.set(slot, stack);
        }
        this.updateState();
    }

    @Override
    public void clear() {
        this.inventory.clear();
        this.updateState();
    }

    public void updateState() {
        if (this.world != null && !this.world.isClient()) {
            var state = this.world.getBlockState(this.pos);
            this.cachedEntity = null;
            this.world.updateListeners(this.pos, state, state, Block.NOTIFY_LISTENERS);
        }
    }

    public float getPlayerYaw() {
        return this.playerYaw;
    }
}