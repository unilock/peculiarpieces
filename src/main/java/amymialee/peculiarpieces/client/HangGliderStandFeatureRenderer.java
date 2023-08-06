package amymialee.peculiarpieces.client;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.PeculiarPiecesClient;
import amymialee.peculiarpieces.entity.EquipmentStandEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

public class HangGliderStandFeatureRenderer<T extends EquipmentStandEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    private static final Identifier SKIN = PeculiarPieces.id("textures/entity/gliders/hang_glider.png");
    private final HangGliderEntityModel<T> glider;

    public HangGliderStandFeatureRenderer(FeatureRendererContext<T, M> context, EntityModelLoader loader) {
        super(context);
        this.glider = new HangGliderEntityModel<>(loader.getModelPart(PeculiarPiecesClient.HANG_GLIDER));
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T entity, float f, float g, float h, float j, float k, float l) {
        if (hasGlider(entity)) {
            matrixStack.push();
            matrixStack.translate(0.0, 0.0, 0.1);
            this.getContextModel().copyStateTo(this.glider);
            this.glider.setAngles(entity, f, g, j, k, l);
            var identifier = SKIN;
            try {
                var glider = getGlider(entity);
                if (glider != null && glider.hasCustomName()) {
                    var renameGlider = glider.getName().getString().toLowerCase().split(" ")[0];
                    if (Identifier.isValid(renameGlider)) {
                        var supportIdentifier = PeculiarPieces.id("textures/entity/gliders/%s_glider.png".formatted(renameGlider));
                        if (MinecraftClient.getInstance().getResourceManager().getResource(supportIdentifier).isPresent()) {
                            identifier = supportIdentifier;
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            var vertexConsumer = ItemRenderer.getArmorGlintConsumer(vertexConsumerProvider, RenderLayer.getArmorCutoutNoCull(identifier), false, false);
            this.glider.render(matrixStack, vertexConsumer, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
            matrixStack.pop();
        }
    }

    public static boolean hasGlider(EquipmentStandEntity player) {
        return !player.getGliderItem().isEmpty();
    }

    public static ItemStack getGlider(EquipmentStandEntity player) {
        return player.getGliderItem();
    }
}