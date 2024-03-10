package amymialee.peculiarpieces.component;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.nbt.NbtCompound;

public class DefusedComponent implements AutoSyncedComponent {
    private final CreeperEntity creeper;
    private boolean defused = false;

    public DefusedComponent(CreeperEntity creeper) {
        this.creeper = creeper;
    }

    public boolean getDefused() {
        return this.defused;
    }

    public void setDefused(boolean value) {
        this.defused = value;
        PeculiarEntityComponentInitializer.DEFUSED.sync(this.creeper);
    }

    @Override
    public void readFromNbt(NbtCompound tag) {
        this.defused = tag.getBoolean("defused");
    }

    @Override
    public void writeToNbt(NbtCompound tag) {
        tag.putBoolean("defused", this.defused);
    }
}
