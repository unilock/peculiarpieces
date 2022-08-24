package amymialee.peculiarpieces.screens;

import amymialee.peculiarpieces.PeculiarPieces;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawableHelper;
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
        this.passEvents = false;
        this.backgroundHeight = 133;
        this.playerInventoryTitleY = this.backgroundHeight - 94;
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        this.drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        this.drawTexture(matrices, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        if (handler.getSlot(0).getStack().isEmpty()) {
            RenderSystem.setShaderTexture(0, EMPTY_FEET);
            DrawableHelper.drawTexture(matrices, x + 62, y + 20, getZOffset(), 0, 0, 16, 16, 16, 16);
        }
        if (handler.getSlot(1).getStack().isEmpty()) {
            RenderSystem.setShaderTexture(0, EMPTY_LEGS);
            DrawableHelper.drawTexture(matrices, x + 80, y + 20, getZOffset(), 0, 0, 16, 16, 16, 16);
        }
        if (handler.getSlot(2).getStack().isEmpty()) {
            RenderSystem.setShaderTexture(0, EMPTY_CHEST);
            DrawableHelper.drawTexture(matrices, x + 98, y + 20, getZOffset(), 0, 0, 16, 16, 16, 16);
        }
        if (handler.getSlot(3).getStack().isEmpty()) {
            RenderSystem.setShaderTexture(0, EMPTY_HEAD);
            DrawableHelper.drawTexture(matrices, x + 116, y + 20, getZOffset(), 0, 0, 16, 16, 16, 16);
        }
        if (handler.getSlot(4).getStack().isEmpty()) {
            RenderSystem.setShaderTexture(0, EMPTY_GLIDER);
            DrawableHelper.drawTexture(matrices, x + 134, y + 20, getZOffset(), 0, 0, 16, 16, 16, 16);
        }
        if (handler.getSlot(6).getStack().isEmpty()) {
            RenderSystem.setShaderTexture(0, EMPTY_OFFHAND);
            DrawableHelper.drawTexture(matrices, x + 44, y + 20, getZOffset(), 0, 0, 16, 16, 16, 16);
        }
    }
}