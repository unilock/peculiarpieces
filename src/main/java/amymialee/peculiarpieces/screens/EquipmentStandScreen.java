package amymialee.peculiarpieces.screens;

import amymialee.peculiarpieces.PeculiarPieces;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class EquipmentStandScreen extends HandledScreen<EquipmentStandScreenHandler> {
    private static final Identifier TEXTURE = PeculiarPieces.id("textures/gui/seven_inventory.png");
    private static final Identifier EMPTY_OFFHAND = new Identifier("textures/item/empty_armor_slot_shield.png");
    private static final Identifier EMPTY_FEET = new Identifier("textures/item/empty_armor_slot_boots.png");
    private static final Identifier EMPTY_LEGS = new Identifier("textures/item/empty_armor_slot_leggings.png");
    private static final Identifier EMPTY_CHEST = new Identifier("textures/item/empty_armor_slot_chestplate.png");
    private static final Identifier EMPTY_HEAD = new Identifier("textures/item/empty_armor_slot_helmet.png");
    private static final Identifier EMPTY_GLIDER = PeculiarPieces.id("textures/gui/empty_glider.png");

    public EquipmentStandScreen(EquipmentStandScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundHeight = 133;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        this.renderBackground(ctx);
        super.render(ctx, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(ctx, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(DrawContext ctx, float delta, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        ctx.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        if (this.handler.getSlot(0).getStack().isEmpty()) {
            ctx.drawTexture(EMPTY_FEET, this.x + 62, this.y + 20, 0, 0, 0, 16, 16, 16, 16);
        }
        if (this.handler.getSlot(1).getStack().isEmpty()) {
            ctx.drawTexture(EMPTY_LEGS, this.x + 80, this.y + 20, 0, 0, 0, 16, 16, 16, 16);
        }
        if (this.handler.getSlot(2).getStack().isEmpty()) {
            ctx.drawTexture(EMPTY_CHEST, this.x + 98, this.y + 20, 0, 0, 0, 16, 16, 16, 16);
        }
        if (this.handler.getSlot(3).getStack().isEmpty()) {
            ctx.drawTexture(EMPTY_HEAD, this.x + 116, this.y + 20, 0, 0, 0, 16, 16, 16, 16);
        }
        if (this.handler.getSlot(4).getStack().isEmpty()) {
            ctx.drawTexture(EMPTY_GLIDER, this.x + 134, this.y + 20, 0, 0, 0, 16, 16, 16, 16);
        }
        if (this.handler.getSlot(6).getStack().isEmpty()) {
            ctx.drawTexture(EMPTY_OFFHAND, this.x + 44, this.y + 20, 0, 0, 0, 16, 16, 16, 16);
        }
    }
}