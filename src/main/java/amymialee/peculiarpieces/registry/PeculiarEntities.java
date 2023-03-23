package amymialee.peculiarpieces.registry;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.entity.EquipmentStandEntity;
import amymialee.peculiarpieces.entity.TeleportItemEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class PeculiarEntities {
    public static final EntityType<TeleportItemEntity> TELEPORT_ITEM_ENTITY = registerEntity("teleport_item_entity", FabricEntityTypeBuilder.<TeleportItemEntity>create(SpawnGroup.MISC, TeleportItemEntity::new).dimensions(EntityDimensions.fixed(0.5f, 0.5f)).trackRangeChunks(6).trackedUpdateRate(20).build());
    public static final EntityType<EquipmentStandEntity> EQUIPMENT_STAND_ENTITY = registerEntity("equipment_stand_entity", FabricEntityTypeBuilder.<EquipmentStandEntity>create(SpawnGroup.MISC, EquipmentStandEntity::new).dimensions(EntityDimensions.fixed(0.5f, 1.8f)).trackRangeChunks(6).trackedUpdateRate(Integer.MAX_VALUE).build());

    @SuppressWarnings("ConstantConditions")
    public static void init() {
        FabricDefaultAttributeRegistry.register(EQUIPMENT_STAND_ENTITY, HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0f).add(EntityAttributes.GENERIC_MAX_HEALTH, 20));
    }

    public static <T extends Entity> EntityType<T> registerEntity(String name, EntityType<T> entity) {
        Registry.register(Registries.ENTITY_TYPE, PeculiarPieces.id(name), entity);
        return entity;
    }
}