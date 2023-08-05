package amymialee.peculiarpieces.recipe;

import com.google.gson.JsonObject;
import amymialee.peculiarpieces.PeculiarPieces;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapedRecipe;
import net.minecraft.util.Identifier;

public class ShapedNbtRecipe extends ShapedRecipe {

    public ShapedNbtRecipe(ShapedRecipe copy) {
        super(copy.getId(), copy.getGroup(), copy.getCategory(), copy.getWidth(), copy.getHeight(), copy.getIngredients(), copy.getOutput(null), copy.showNotification());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PeculiarPieces.SHAPED_NBT_CRAFTING_SERIALZIER;
    }
    
    public static class Serializer extends ShapedRecipe.Serializer {
        
        @Override
        public ShapedRecipe read(Identifier identifier, JsonObject jsonObject) {
            NbtCompound data = NbtRecipe.extractData(jsonObject);
            ShapedRecipe rec = super.read(identifier, jsonObject);
            rec.getOutput(null).setNbt(data);
            return new ShapedNbtRecipe(rec);
        }
        
        @Override
        public ShapedRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            return new ShapedNbtRecipe(super.read(identifier, packetByteBuf));
        }
        
    }
    
}
