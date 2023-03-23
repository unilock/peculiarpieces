package amymialee.peculiarpieces.mixin;

import net.minecraft.block.Block;
import net.minecraft.block.PistonBlock;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PistonBlock.class)
public class PistonBlockMixin extends Block {
    public PistonBlockMixin(Settings settings) {
        super(settings);
    }
}