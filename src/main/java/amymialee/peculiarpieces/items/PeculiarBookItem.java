package amymialee.peculiarpieces.items;

import amymialee.peculiarpieces.PeculiarPieces;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import vazkii.patchouli.api.PatchouliAPI;
import vazkii.patchouli.common.base.PatchouliSounds;
import vazkii.patchouli.common.book.Book;
import vazkii.patchouli.common.book.BookRegistry;

public class PeculiarBookItem extends Item {
    public PeculiarBookItem(FabricItemSettings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);
        Book book = getBook();
        if (book == null) {
            return new TypedActionResult<>(ActionResult.FAIL, stack);
        }
        if (player instanceof ServerPlayerEntity serverPlayer) {
            PatchouliAPI.get().openBookGUI(serverPlayer, book.id);
            SoundEvent sfx = PatchouliSounds.getSound(book.openSound, PatchouliSounds.BOOK_OPEN);
            player.playSound(sfx, 1F, (float) (0.7 + Math.random() * 0.4));
        }
        return new TypedActionResult<>(ActionResult.SUCCESS, stack);
    }

    public Book getBook() {
        return BookRegistry.INSTANCE.books.get(PeculiarPieces.id("peculiar"));
    }
}