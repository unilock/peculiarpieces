package amymialee.peculiarpieces.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Accessor
    void setTouchingWater(boolean value);
    @Accessor
    void setBlockStateAtPos(BlockState state);
}