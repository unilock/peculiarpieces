package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.util.BlockEntityWrapper;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(BlockEntity.class)
public class BlockEntityMixin implements BlockEntityWrapper {
    @Mutable
    @Shadow @Final protected BlockPos pos;

    @Override
    public void setPos(BlockPos pos) {
        this.pos = pos;
    }
}