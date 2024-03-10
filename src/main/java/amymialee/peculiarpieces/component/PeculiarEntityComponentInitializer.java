package amymialee.peculiarpieces.component;

import amymialee.peculiarpieces.PeculiarPieces;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.minecraft.entity.mob.CreeperEntity;
import org.jetbrains.annotations.NotNull;

public class PeculiarEntityComponentInitializer implements EntityComponentInitializer {
    public static final ComponentKey<DefusedComponent> DEFUSED = ComponentRegistry.getOrCreate(PeculiarPieces.id("defused"), DefusedComponent.class);

    @Override
    public void registerEntityComponentFactories(@NotNull EntityComponentFactoryRegistry registry) {
        registry.registerFor(CreeperEntity.class, DEFUSED, DefusedComponent::new);
    }
}