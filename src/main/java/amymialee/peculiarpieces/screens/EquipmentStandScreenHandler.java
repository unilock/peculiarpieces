package amymialee.peculiarpieces.screens;

import amymialee.peculiarpieces.PeculiarPieces;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class EquipmentStandScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    public EquipmentStandScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(6));
    }

    public EquipmentStandScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(PeculiarPieces.EQUIPMENT_STAND_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        this.addSlot(new Slot(inventory, 2, 35 + (2 * 18), 20) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return LivingEntity.getPreferredEquipmentSlot(stack) == EquipmentSlot.FEET;
            }
        });
        this.addSlot(new Slot(inventory, 3, 35 + (3 * 18), 20) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return LivingEntity.getPreferredEquipmentSlot(stack) == EquipmentSlot.LEGS;
            }
        });
        this.addSlot(new Slot(inventory, 4, 35 + (4 * 18), 20) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return LivingEntity.getPreferredEquipmentSlot(stack) == EquipmentSlot.CHEST;
            }
        });
        this.addSlot(new Slot(inventory, 5, 35 + (5 * 18), 20) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return LivingEntity.getPreferredEquipmentSlot(stack) == EquipmentSlot.HEAD;
            }
        });
        this.addSlot(new Slot(inventory, 0, 35, 20));
        this.addSlot(new Slot(inventory, 1, 35 + 18, 20));
        for(int j = 0; j < 3; ++j) {
            for(int k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, j * 18 + 51));
            }
        }
        for(int j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 109));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack transferSlot(PlayerEntity player, int index) {
        ItemStack copy = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasStack()) {
            ItemStack itemStack = slot.getStack();
            copy = itemStack.copy();
            if (index < 6 ? !this.insertItem(itemStack, 6, this.slots.size(), true) : !this.insertItem(itemStack, 0, 6, false)) {
                return ItemStack.EMPTY;
            }
            if (itemStack.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
        }
        return copy;
    }

    public void close(PlayerEntity player) {
        super.close(player);
        this.inventory.onClose(player);
    }
}