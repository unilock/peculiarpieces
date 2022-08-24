package amymialee.peculiarpieces.client;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.entity.EquipmentStandEntity;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.util.Identifier;

public class EquipmentStandEntityRenderer extends BipedEntityRenderer<EquipmentStandEntity, BipedEntityModel<EquipmentStandEntity>> {
    private static final Identifier TEXTURE = PeculiarPieces.id("textures/item/equipment_stand.png");

    public EquipmentStandEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, new BipedEntityModel<>(ctx.getPart(EntityModelLayers.SKELETON)), 0.0f);
        this.addFeature(new ArmorFeatureRenderer<>(this,
                        new BipedEntityModel<>(ctx.getPart(EntityModelLayers.SKELETON_INNER_ARMOR)),
                        new BipedEntityModel<>(ctx.getPart(EntityModelLayers.SKELETON_OUTER_ARMOR))));
        this.addFeature(new HangGliderStandFeatureRenderer<>(this, ctx.getModelLoader()));
    }

    @Override
    public Identifier getTexture(EquipmentStandEntity abstractSkeletonEntity) {
        return TEXTURE;
    }

    @Override
    protected float getAnimationProgress(EquipmentStandEntity entity, float tickDelta) {
        return 0.5f;
    }
}