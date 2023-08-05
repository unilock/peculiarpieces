package amymialee.peculiarpieces.recipe;

import com.google.gson.JsonObject;
import amymialee.peculiarpieces.PeculiarPieces;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.ShapelessRecipe;
import net.minecraft.util.Identifier;

public class ShapelessNbtRecipe extends ShapelessRecipe {

    public ShapelessNbtRecipe(ShapelessRecipe copy) {
        super(copy.getId(), copy.getGroup(), copy.getCategory(), copy.getOutput(null), copy.getIngredients());
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return PeculiarPieces.SHAPELESS_NBT_CRAFTING_SERIALZIER;
    }
    
    public static class Serializer extends ShapelessRecipe.Serializer {
        
        @Override
        public ShapelessRecipe read(Identifier identifier, JsonObject jsonObject) {
            NbtCompound data = NbtRecipe.extractData(jsonObject);
            ShapelessRecipe rec = super.read(identifier, jsonObject);
            rec.getOutput(null).setNbt(data);
            return new ShapelessNbtRecipe(rec);
        }
        
        @Override
        public ShapelessRecipe read(Identifier identifier, PacketByteBuf packetByteBuf) {
            return new ShapelessNbtRecipe(super.read(identifier, packetByteBuf));
        }
        
    }
    
}
