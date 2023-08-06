package amymialee.peculiarpieces.screens;

import amymialee.peculiarpieces.PeculiarPieces;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;

public class FishTankScreenHandler extends ScreenHandler {
    private final Inventory inventory;

    public FishTankScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, new SimpleInventory(1));
    }

    public FishTankScreenHandler(int syncId, PlayerInventory playerInventory, Inventory inventory) {
        super(PeculiarPieces.FISH_TANK_SCREEN_HANDLER, syncId);
        this.inventory = inventory;
        this.addSlot(new Slot(inventory, 0, 80, 20) {
            @Override
            public boolean canInsert(ItemStack stack) {
                return stack.isOf(Items.TROPICAL_FISH_BUCKET) || stack.isOf(Items.PUFFERFISH_BUCKET) || stack.isOf(Items.SALMON_BUCKET) || stack.isOf(Items.COD_BUCKET);
            }
        });
        for(var j = 0; j < 3; ++j) {
            for(var k = 0; k < 9; ++k) {
                this.addSlot(new Slot(playerInventory, k + j * 9 + 9, 8 + k * 18, j * 18 + 51));
            }
        }
        for(var j = 0; j < 9; ++j) {
            this.addSlot(new Slot(playerInventory, j, 8 + j * 18, 109));
        }
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int index) {
        var copy = ItemStack.EMPTY;
        var slot = this.slots.get(index);
        if (slot.hasStack()) {
            var stack = slot.getStack();
            copy = stack.copy();
            if (index == 0) {
                if (!this.insertItem(stack, 1, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.insertItem(stack, 0, 1, false)) {
                return ItemStack.EMPTY;
            }
            if (stack.isEmpty()) {
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