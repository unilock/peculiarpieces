package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.registry.PeculiarItems;
import com.mojang.blaze3d.systems.RenderSystem;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow @Final private Random random;
    private static final Identifier PECULIAR_ICONS_TEXTURE = PeculiarPieces.id("textures/gui/icons.png");

    @Inject(method = "renderHealthBar", at = @At("TAIL"))
    private void PeculiarPieces$TotemHealth(DrawContext ctx, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci) {
        var j = MathHelper.ceil((double)maxHealth / 2.0);
        var q = y;
        if (lastHealth + absorption <= 4) {
            q += this.random.nextInt(2);
        }
        if (-1 < j && 0 == regeneratingHeartIndex) {
            q -= 2;
        }
        for (var hand : Hand.values()) {
            var itemStack = player.getStackInHand(hand);
            if (itemStack.isOf(Items.TOTEM_OF_UNDYING)) {
                this.drawTotem(ctx, x, q, 0, blinking);
                this.drawTotem(ctx, x, q, 1, false);
                return;
            }
        }
        var optionalComponent = TrinketsApi.getTrinketComponent(player);
        if (optionalComponent.isPresent()) {
            var token = optionalComponent.get().isEquipped(PeculiarItems.TOKEN_OF_UNDYING);
            var emblem = optionalComponent.get().isEquipped(PeculiarItems.EVERLASTING_EMBLEM);
            if (token || emblem) {
                this.drawTotem(ctx, x, q, 0, blinking);
                if (token) {
                    this.drawTotem(ctx, x, q, 2, false);
                } else {
                    if (!player.getItemCooldownManager().isCoolingDown(PeculiarItems.EVERLASTING_EMBLEM)) {
                        this.drawTotem(ctx, x, q, 3, false);
                    } else if (player.getItemCooldownManager().getCooldownProgress(PeculiarItems.EVERLASTING_EMBLEM, 1) < 0.5f) {
                        this.drawTotem(ctx, x, q, 3, true);
                    }
                }
            }
        }
    }

    private void drawTotem(DrawContext ctx, int x, int y, int offset, boolean altTexture) {
        ctx.drawTexture(PECULIAR_ICONS_TEXTURE, x, y - 3, 0, 9 * offset, altTexture ? 9 : 0, 9, 9, 64, 32);
    }
}