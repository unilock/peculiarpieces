package amymialee.peculiarpieces.client;

import amymialee.peculiarpieces.VisibleBarriersAccess;
import xyz.amymialee.visiblebarriers.VisibleBarriers;

public class VisibleBarriersCompat {

    public static void init() {
        VisibleBarriersAccess.barriers = VisibleBarriers::areBarriersEnabled;
        VisibleBarriersAccess.structureVoids = VisibleBarriers::areStructureVoidsEnabled;
        VisibleBarriersAccess.visibility = VisibleBarriers::isVisibilityEnabled;
    }
    
}
