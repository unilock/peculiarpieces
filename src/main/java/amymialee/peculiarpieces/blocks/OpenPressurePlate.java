package amymialee.peculiarpieces.blocks;

import net.minecraft.block.PressurePlateBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

import java.util.List;

public class OpenPressurePlate extends PressurePlateBlock {
    private final boolean invis;
    private final int weight;

    public OpenPressurePlate(boolean invis, int weight, Settings settings, SoundEvent depressSound, SoundEvent pressSound) {
        super(ActivationRule.EVERYTHING, settings, depressSound, pressSound);
        this.invis = invis;
        this.weight = weight;
    }

    protected void playPressSound(WorldAccess world, BlockPos pos) {
        if (!invis) {
            super.playPressSound(world, pos);
        }
    }

    protected void playDepressSound(WorldAccess world, BlockPos pos) {
        if (!invis) {
            super.playDepressSound(world, pos);
        }
    }

    protected int getRedstoneOutput(World world, BlockPos pos) {
        Box box = BOX.offset(pos);
        List<? extends Entity> list;
        switch (this.weight) {
            case 0 -> list = world.getOtherEntities(null, box);
            case 1 -> list = world.getNonSpectatingEntities(LivingEntity.class, box);
            case 2 -> list = world.getNonSpectatingEntities(PlayerEntity.class, box);
            default -> {
                return 0;
            }
        }
        if (!list.isEmpty()) {
            for (Entity entity : list) {
                if (!entity.canAvoidTraps()) {
                    return 15;
                }
            }
        }
        return 0;
    }
}