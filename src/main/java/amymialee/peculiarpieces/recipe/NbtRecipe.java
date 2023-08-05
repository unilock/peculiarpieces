package amymialee.peculiarpieces.recipe;

import com.google.gson.JsonObject;
import com.mojang.serialization.JsonOps;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtOps;
import net.minecraft.util.JsonHelper;

public class NbtRecipe {

    public static NbtCompound extractData(JsonObject obj) {
        JsonObject result = JsonHelper.getObject(obj, "result");
        if (result.has("data")) {
            NbtCompound data = (NbtCompound) JsonOps.INSTANCE.convertTo(NbtOps.INSTANCE, JsonHelper.getObject(result, "data"));
            result.remove("data");
            return data;
        }
        return null;
    }
    
}
