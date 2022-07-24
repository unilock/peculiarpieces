package amymialee.peculiarpieces.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.ProjectileDamageSource;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.potion.Potions;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndermanEntity.class)
public class EndermanEntityMixin extends HostileEntity {
    protected EndermanEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "damage", at = @At("HEAD"), cancellable = true)
    public void PeculiarPieces$SplashingDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        if (!this.isInvulnerableTo(source)) {
            if (source instanceof ProjectileDamageSource) {
                if (source.getSource() instanceof ArrowEntity arrow && ((ArrowEntityAccessor) arrow).getPotion() == Potions.WATER) {
                    cir.setReturnValue(super.damage(source, amount * 2));
                }
            }
        }
    }
}