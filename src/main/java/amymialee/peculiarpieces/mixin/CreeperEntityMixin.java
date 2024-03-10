package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.component.PeculiarEntityComponentInitializer;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreeperEntity.class)
public class CreeperEntityMixin extends HostileEntity {
    @Shadow @Final private static TrackedData<Boolean> IGNITED;

    protected CreeperEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "explode", at = @At("HEAD"), cancellable = true)
    private void PeculiarPieces$ExplosionCancel(CallbackInfo ci) {
        if (this.getDefused()) ci.cancel();
    }

    @Inject(method = "interactMob", at = @At("HEAD"), cancellable = true)
    private void PeculiarPieces$ExtraInteractions(PlayerEntity player2, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        var itemStack = player2.getStackInHand(hand);
        if (itemStack.isOf(Items.SHEARS) && !this.getDefused()) {
            if (!this.getWorld().isClient) {
                this.getWorld().playSoundFromEntity(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.PLAYERS, 1.0f, 1.0f);
                this.setDefused(true);
                var itemEntity = this.dropStack(new ItemStack(Items.STRING), 1);
                if (itemEntity != null) {
                    itemEntity.setVelocity(itemEntity.getVelocity().add((this.random.nextFloat() - this.random.nextFloat()) * 0.1f, this.random.nextFloat() * 0.05f, (this.random.nextFloat() - this.random.nextFloat()) * 0.1f));
                }
                this.emitGameEvent(GameEvent.SHEAR, player2);
                itemStack.damage(1, player2, player -> player.sendToolBreakStatus(hand));
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        } else if (itemStack.isOf(Items.STRING) && this.getDefused()) {
            if (!this.getWorld().isClient) {
                this.getWorld().playSoundFromEntity(null, this, SoundEvents.BLOCK_WOOL_PLACE, SoundCategory.PLAYERS, 1.0f, 1.0f);
                this.setDefused(false);
                itemStack.decrement(1);
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        } else if (itemStack.isOf(Items.WATER_BUCKET) && this.dataTracker.get(IGNITED)) {
            if (!this.getWorld().isClient) {
                itemStack.decrement(1);
                player2.setStackInHand(hand, BucketItem.getEmptiedStack(itemStack, player2));
                this.getWorld().playSoundFromEntity(null, this, SoundEvents.ITEM_BUCKET_EMPTY, SoundCategory.PLAYERS, 1.0f, 1.0f);
                this.dataTracker.set(IGNITED, false);
                itemStack.decrement(1);
                cir.setReturnValue(ActionResult.SUCCESS);
            }
        }
    }

    @Unique
    private boolean getDefused() {
        return PeculiarEntityComponentInitializer.DEFUSED.get((CreeperEntity) (Object) this).getDefused();
    }

    @Unique
    private void setDefused(boolean value) {
        PeculiarEntityComponentInitializer.DEFUSED.get((CreeperEntity) (Object) this).setDefused(value);
    }
}