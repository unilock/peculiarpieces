package amymialee.peculiarpieces;

import java.util.function.BooleanSupplier;

public class VisibleBarriersAccess {
    
    public static BooleanSupplier structureVoids = () -> false;
    public static BooleanSupplier barriers = () -> false;
    public static BooleanSupplier visibility = () -> false;

    public static boolean areStructureVoidsEnabled() {
        return structureVoids.getAsBoolean();
    }

    public static boolean areBarriersEnabled() {
        return barriers.getAsBoolean();
    }

    public static boolean isVisibilityEnabled() {
        return visibility.getAsBoolean();
    }

}
