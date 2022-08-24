package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.registry.PeculiarItems;
import net.minecraft.inventory.ContainerLock;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ContainerLock.class)
public class ContainerLockMixin {
    @Inject(method = "canOpen", at = @At("HEAD"), cancellable = true)
    private void PeculiarPieces$MasterKey(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.isOf(PeculiarItems.MASTER_KEY)) {
            cir.setReturnValue(true);
        }
    }
}