package xyz.brassgoggledcoders.iberiarediscovered.recipe.ingredient;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

public class PotionIngredientSerializer implements IIngredientSerializer<PotionIngredient> {
    @Override
    @Nonnull
    public PotionIngredient parse(FriendlyByteBuf buffer) {
        return PotionIngredient.of(ForgeRegistries.POTIONS.getValue(buffer.readResourceLocation()));
    }

    @Override
    @Nonnull
    public PotionIngredient parse(@Nonnull JsonObject json) {
        String potionName = GsonHelper.getAsString(json, "potion");
        Potion potion = ForgeRegistries.POTIONS.getValue(new ResourceLocation(potionName));
        if (potion != null) {
            return PotionIngredient.of(potion);
        } else {
            throw new JsonParseException("Failed to find Potion: " + potionName);
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public void write(FriendlyByteBuf buffer, PotionIngredient ingredient) {
        buffer.writeResourceLocation(Objects.requireNonNull(ForgeRegistries.POTIONS.getKey(ingredient.getPotion())));
    }
}
