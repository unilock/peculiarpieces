package amymialee.peculiarpieces.mixin;

import amymialee.peculiarpieces.util.LockableBlockEntityWrapper;
import net.minecraft.block.entity.LockableContainerBlockEntity;
import net.minecraft.inventory.ContainerLock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(LockableContainerBlockEntity.class)
public class LockableContainerBlockEntityMixin implements LockableBlockEntityWrapper {
    @Shadow private ContainerLock lock;

    @Override
    public ContainerLock getLock() {
        return lock;
    }

    @Override
    public void setLock(ContainerLock lock) {
        this.lock = lock;
    }
}