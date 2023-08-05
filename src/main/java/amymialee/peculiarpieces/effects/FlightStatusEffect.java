package amymialee.peculiarpieces.effects;

import amymialee.peculiarpieces.PeculiarPieces;
import io.github.ladysnake.pal.AbilitySource;
import io.github.ladysnake.pal.Pal;
import io.github.ladysnake.pal.VanillaAbilities;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class FlightStatusEffect extends OpenStatusEffect {
    public static final AbilitySource FLIGHT_POTION = Pal.getAbilitySource(PeculiarPieces.id("flight_potion"));

    public FlightStatusEffect(StatusEffectCategory statusEffectCategory, int color) {
        super(statusEffectCategory, color);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onApplied(entity, attributes, amplifier);
        if (!entity.getWorld().isClient() && entity instanceof PlayerEntity player) {
            Pal.grantAbility(player, VanillaAbilities.ALLOW_FLYING, FLIGHT_POTION);
        }
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onRemoved(entity, attributes, amplifier);
        if (!entity.getWorld().isClient() && entity instanceof PlayerEntity player) {
            Pal.revokeAbility(player, VanillaAbilities.ALLOW_FLYING, FLIGHT_POTION);
        }
    }
}