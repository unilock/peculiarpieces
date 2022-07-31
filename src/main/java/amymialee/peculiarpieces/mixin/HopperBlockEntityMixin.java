package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.blockentities.CreativeBarrelBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.HopperBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(HopperBlockEntity.class)
public abstract class HopperBlockEntityMixin {
    @Inject(method = "insert", at = @At("HEAD"), cancellable = true)
    private static void insert(World world, BlockPos pos, BlockState state, Inventory inventory, CallbackInfoReturnable<Boolean> cir) {
        BlockEntity entity = getOutputBlockEntity(world, pos, state);
        if (entity instanceof CreativeBarrelBlockEntity) {
            for (int i = 0; i < inventory.size(); ++i) {
                if (!inventory.getStack(i).isEmpty()) {
                    inventory.removeStack(i, 1);
                    cir.setReturnValue(true);
                    return;
                }
            }
        }
    }

    private static BlockEntity getOutputBlockEntity(World world, BlockPos pos, BlockState state) {
        Direction direction = state.get(HopperBlock.FACING);
        return world.getBlockEntity(pos.offset(direction));
    }
}