package amymialee.peculiarpieces.util;

import net.minecraft.inventory.ContainerLock;

public interface LockableBlockEntityWrapper {
    ContainerLock getLock();
    void setLock(ContainerLock lock);
}