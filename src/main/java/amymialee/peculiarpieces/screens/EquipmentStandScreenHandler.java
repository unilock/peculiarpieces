package amymialee.peculiarpieces.screens;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.registry.PeculiarItems;
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
        this(syncId, playerInventory, new SimpleInventory(7));
    }

    public EquipmentStandScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(PeculiarPieces.EQUIPMENT_STAND_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        this.addSlot(new Slot(inventory, 2, 26 + (2 * 18), 20) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return LivingEntity.getPreferredEquipmentSlot(stack) == EquipmentSlot.FEET;
            }
        });
        this.addSlot(new Slot(inventory, 3, 26 + (3 * 18), 20) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return LivingEntity.getPreferredEquipmentSlot(stack) == EquipmentSlot.LEGS;
            }
        });
        this.addSlot(new Slot(inventory, 4, 26 + (4 * 18), 20) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return LivingEntity.getPreferredEquipmentSlot(stack) == EquipmentSlot.CHEST;
            }
        });
        this.addSlot(new Slot(inventory, 5, 26 + (5 * 18), 20) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return LivingEntity.getPreferredEquipmentSlot(stack) == EquipmentSlot.HEAD;
            }
        });
        this.addSlot(new Slot(inventory, 6, 26 + (6 * 18), 20) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(PeculiarItems.HANG_GLIDER);
            }
        });
        this.addSlot(new Slot(inventory, 0, 26, 20));
        this.addSlot(new Slot(inventory, 1, 26 + 18, 20));
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
    public ItemStack quickMove(PlayerEntity player, int index) {
        ItemStack copy = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasStack()) {
            ItemStack itemStack = slot.getStack();
            copy = itemStack.copy();
            if (index < this.inventory.size() ? !this.insertItem(itemStack, this.inventory.size(), this.slots.size(), true) : !this.insertItem(itemStack, 0, this.inventory.size(), false)) {
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

    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.inventory.onClose(player);
    }
}