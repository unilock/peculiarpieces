package amymialee.peculiarpieces.blocks.port;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.PressurePlateBlock.ActivationRule;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager.Builder;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class LegacyPressurePlateBlock extends AbstractLegacyPressurePlateBlock {
    public static final BooleanProperty POWERED;
    private final ActivationRule type;
    private final SoundEvent depressSound, pressSound;

    protected LegacyPressurePlateBlock(ActivationRule type, Settings settings, SoundEvent depressSound, SoundEvent pressSound) {
        super(settings);
        this.setDefaultState(this.getDefaultState().with(POWERED, false));
        this.type = type;
        this.depressSound = depressSound;
        this.pressSound = pressSound;
    }

    @Override
    protected int getRedstoneOutput(BlockState state) {
        return state.get(POWERED) ? 15 : 0;
    }

    @Override
    protected BlockState setRedstoneOutput(BlockState state, int rsOut) {
        return state.with(POWERED, rsOut > 0);
    }

    @Override
    protected void playPressSound(WorldAccess world, BlockPos pos) {
        world.playSound(null, pos, this.pressSound, SoundCategory.BLOCKS, 0.3F, 0.6F);
    }

    @Override
    protected void playDepressSound(WorldAccess world, BlockPos pos) {
        world.playSound(null, pos, this.depressSound, SoundCategory.BLOCKS, 0.3F, 0.6F);
    }

    @Override
    protected int getRedstoneOutput(World world, BlockPos pos) {
        return 0;
    }

    @Override
    protected void appendProperties(Builder<Block, BlockState> builder) {
        super.appendProperties(builder);
        builder.add(POWERED);
    }

    static {
        POWERED = Properties.POWERED;
    }
}