package amymialee.peculiarpieces.items;

import amymialee.peculiarpieces.PeculiarPieces;
import dev.emi.trinkets.api.SlotReference;
import io.github.ladysnake.pal.AbilitySource;
import io.github.ladysnake.pal.Pal;
import io.github.ladysnake.pal.VanillaAbilities;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public class FlightRingItem extends DispensableTrinketItem {
    public static final AbilitySource FLIGHT_RING = Pal.getAbilitySource(PeculiarPieces.id("flight_ring"));

    public FlightRingItem(FabricItemSettings settings) {
        super(settings);
    }

    @Override
    public void onEquip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        super.onEquip(stack, slot, entity);
        if (!entity.world.isClient() && entity instanceof PlayerEntity player) {
            Pal.grantAbility(player, VanillaAbilities.ALLOW_FLYING, FLIGHT_RING);
        }
    }

    @Override
    public void onUnequip(ItemStack stack, SlotReference slot, LivingEntity entity) {
        super.onUnequip(stack, slot, entity);
        if (!entity.world.isClient() && entity instanceof PlayerEntity player) {
            Pal.revokeAbility(player, VanillaAbilities.ALLOW_FLYING, FLIGHT_RING);
        }
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return true;
    }
}