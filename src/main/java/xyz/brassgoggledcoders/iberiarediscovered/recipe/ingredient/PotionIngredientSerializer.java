package xyz.brassgoggledcoders.iberiarediscovered.recipe.ingredient;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.network.PacketBuffer;
import net.minecraft.potion.Potion;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

public class PotionIngredientSerializer implements IIngredientSerializer<PotionIngredient> {
    @Override
    @Nonnull
    public PotionIngredient parse(PacketBuffer buffer) {
        return PotionIngredient.of(ForgeRegistries.POTION_TYPES.getValue(buffer.readResourceLocation()));
    }

    @Override
    @Nonnull
    public PotionIngredient parse(@Nonnull JsonObject json) {
        String potionName = JSONUtils.getString(json, "potion");
        Potion potion = ForgeRegistries.POTION_TYPES.getValue(new ResourceLocation(potionName));
        if (potion != null) {
            return PotionIngredient.of(potion);
        } else {
            throw new JsonParseException("Failed to find Potion: " + potionName);
        }
    }

    @Override
    @ParametersAreNonnullByDefault
    public void write(PacketBuffer buffer, PotionIngredient ingredient) {
        buffer.writeResourceLocation(Objects.requireNonNull(ingredient.getPotion().getRegistryName()));
    }
}
