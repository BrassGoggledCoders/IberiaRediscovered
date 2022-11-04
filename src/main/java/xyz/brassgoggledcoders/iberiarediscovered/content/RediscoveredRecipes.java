package xyz.brassgoggledcoders.iberiarediscovered.content;

import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IIngredientSerializer;
import xyz.brassgoggledcoders.iberiarediscovered.recipe.ingredient.PotionIngredient;
import xyz.brassgoggledcoders.iberiarediscovered.recipe.ingredient.PotionIngredientSerializer;

@SuppressWarnings("unused")
public class RediscoveredRecipes {
    public static IIngredientSerializer<PotionIngredient> POTION_INGREDIENT = CraftingHelper.register(
            PotionIngredient.NAME,
            new PotionIngredientSerializer()
    );

    public static void setup() {

    }
}
