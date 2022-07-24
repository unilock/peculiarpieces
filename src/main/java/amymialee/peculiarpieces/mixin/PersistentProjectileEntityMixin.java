package amymialee.peculiarpieces.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.potion.Potions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(PersistentProjectileEntity.class)
public class PersistentProjectileEntityMixin {
    @Redirect(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getType()Lnet/minecraft/entity/EntityType;"))
    protected EntityType<?> PeculiarPieces$SplashingEnderman(Entity instance) {
        PersistentProjectileEntity this2 = ((PersistentProjectileEntity) ((Object) this));
        if (this2 instanceof ArrowEntity arrow && ((ArrowEntityAccessor) arrow).getPotion() == Potions.WATER) {
            return EntityType.CREEPER;
        }
        return instance.getType();
    }
}