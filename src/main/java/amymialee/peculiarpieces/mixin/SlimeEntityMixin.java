package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.registry.PeculiarItems;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SlimeEntity.class)
public abstract class SlimeEntityMixin extends MobEntityMixin {
    @Shadow public abstract int getSize();

    @Override
    public void PeculiarPieces$InteractMobHead(CallbackInfoReturnable<ActionResult> cir) {
        if (this.getSize() == 1) {
            if (!this.world.isClient) {
                if (this.dropStack(new ItemStack(PeculiarItems.SLIME)) != null) {
                    this.discard();
                    cir.setReturnValue(ActionResult.SUCCESS);
                }
            }
        }
    }
}