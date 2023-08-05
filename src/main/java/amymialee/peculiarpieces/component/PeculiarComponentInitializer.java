package amymialee.peculiarpieces.component;

import amymialee.peculiarpieces.PeculiarPieces;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.chunk.ChunkComponentInitializer;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;

public class PeculiarComponentInitializer implements ChunkComponentInitializer {
    public static final ComponentKey<WardingComponent> WARDING = ComponentRegistry.getOrCreate(PeculiarPieces.id("warding"), WardingComponent.class);

    @Override
    public void registerChunkComponentFactories(ChunkComponentFactoryRegistry registry) {
        registry.register(WARDING, WardingComponent::new);
    }
}