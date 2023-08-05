package amymialee.peculiarpieces.mixin;

import net.minecraft.block.Block;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

import java.util.Optional;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.VisibleBarriersAccess;
import amymialee.peculiarpieces.component.PeculiarComponentInitializer;
import amymialee.peculiarpieces.component.WardingComponent;
import amymialee.peculiarpieces.registry.PeculiarItems;

@Mixin(ClientWorld.class)
public class ClientWorldMixin {
    @Inject(method = "randomBlockDisplayTick", at = @At("HEAD"))
    public void randomBlockDisplayTick(int centerX, int centerY, int centerZ, int radius, Random random, Block block, BlockPos.Mutable pos, CallbackInfo ci) {
        if (VisibleBarriersAccess.isVisibilityEnabled()) {
            ClientWorld world = ((ClientWorld) ((Object) this));
            Optional<WardingComponent> component = PeculiarComponentInitializer.WARDING.maybeGet(world.getChunk(pos));
            if (component.isPresent()) {
                WardingComponent wardingComponent = component.get();
                if (wardingComponent.getWard(pos)) {
                    world.addParticle(PeculiarPieces.WARDING_AURA, pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, 0.0, 0.0, 0.0);
                }
            }
        }
    }
}