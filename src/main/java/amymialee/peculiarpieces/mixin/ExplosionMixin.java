package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.PeculiarPieces;
import amymialee.peculiarpieces.component.PeculiarChunkComponentInitializer;
import amymialee.peculiarpieces.util.WeakExplosionBehavior;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.explosion.Explosion;
import net.minecraft.world.explosion.ExplosionBehavior;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Explosion.class)
public class ExplosionMixin {
    private static final ExplosionBehavior WEAK_BEHAVIOR = new WeakExplosionBehavior();
    @Mutable @Shadow @Final private Explosion.DestructionType destructionType;
    @Mutable @Shadow @Final private ExplosionBehavior behavior;
    @Shadow @Final private World world;
    @Shadow @Final private ObjectArrayList<BlockPos> affectedBlocks;

    @Inject(method = "<init>(Lnet/minecraft/world/World;Lnet/minecraft/entity/Entity;Lnet/minecraft/entity/damage/DamageSource;Lnet/minecraft/world/explosion/ExplosionBehavior;DDDFZLnet/minecraft/world/explosion/Explosion$DestructionType;)V", at = @At("TAIL"))
    public void PeculiarPieces$ExplosionBreaks(World world, Entity entity, DamageSource damageSource, ExplosionBehavior behavior, double x, double y, double z, float power, boolean createFire, Explosion.DestructionType destructionType, CallbackInfo ci) {
        if (!world.getGameRules().getBoolean(PeculiarPieces.DO_EXPLOSIONS_BREAK)) {
            this.destructionType = Explosion.DestructionType.KEEP;
            this.behavior = WEAK_BEHAVIOR;
            return;
        }
        if (world.getGameRules().getBoolean(PeculiarPieces.DO_EXPLOSIONS_ALWAYS_DROP) && this.destructionType == Explosion.DestructionType.DESTROY_WITH_DECAY) {
            this.destructionType = Explosion.DestructionType.DESTROY;
        }
    }

    @Inject(method = "collectBlocksAndDamageEntities", at = @At("TAIL"))
    public void PeculiarPieces$ExplosionResistWarding(CallbackInfo ci) {
        for (var i = 0; i < this.affectedBlocks.size(); i++) {
            var pos = this.affectedBlocks.get(i);
            var component = PeculiarChunkComponentInitializer.WARDING.maybeGet(this.world.getChunk(pos));
            if (component.isPresent()) {
                var wardingComponent = component.get();
                if (wardingComponent.getWard(pos)) {
                    this.affectedBlocks.remove(pos);
                    i--;
                }
            }
        }
    }
}