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

public class WarpScreen extends HandledScreen<WarpScreenHandler> {
    private static final Identifier TEXTURE = PeculiarPieces.id("textures/gui/single_inventory.png");
    private static final Identifier EMPTY_TEXTURE = PeculiarPieces.id("textures/gui/empty_pearl.png");

    public WarpScreen(WarpScreenHandler handler, PlayerInventory inventory, Text title) {
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
        var i = (this.width - this.backgroundWidth) / 2;
        var j = (this.height - this.backgroundHeight) / 2;
        ctx.drawTexture(TEXTURE, i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        if (this.handler.getSlot(0).getStack().isEmpty()) {
            ctx.drawTexture(EMPTY_TEXTURE, this.x + 80, this.y + 20, 0, 0, 0, 16, 16, 16, 16);
        }
    }
}