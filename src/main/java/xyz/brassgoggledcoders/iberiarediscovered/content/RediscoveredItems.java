package xyz.brassgoggledcoders.iberiarediscovered.content;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.item.Items;
import net.minecraft.potion.Potions;
import net.minecraft.tags.ItemTags;
import net.minecraftforge.common.Tags;
import xyz.brassgoggledcoders.iberiarediscovered.IberiaRediscovered;
import xyz.brassgoggledcoders.iberiarediscovered.item.ElixirOfYouthItem;
import xyz.brassgoggledcoders.iberiarediscovered.item.MedicalSuppliesItem;
import xyz.brassgoggledcoders.iberiarediscovered.recipe.ingredient.PotionIngredient;

@SuppressWarnings("unused")
public class RediscoveredItems {

    public static final ItemEntry<MedicalSuppliesItem> PRIMITIVE_MEDICAL_SUPPLIES = IberiaRediscovered.getRegistrate()
            .object("primitive_medical_supplies")
            .item(properties -> new MedicalSuppliesItem(1, properties))
            .recipe((context, provider) -> ShapelessRecipeBuilder.shapelessRecipe(context::get)
                    .addIngredient(Tags.Items.EGGS)
                    .addIngredient(Items.BOWL)
                    .addIngredient(Items.SWEET_BERRIES)
                    .addCriterion("has_item", RegistrateRecipeProvider.hasItem(Items.SWEET_BERRIES))
                    .build(provider)
            )
            .register();

    public static final ItemEntry<MedicalSuppliesItem> BASIC_MEDICAL_SUPPLIES = IberiaRediscovered.getRegistrate()
            .object("basic_medical_supplies")
            .item(properties -> new MedicalSuppliesItem(2, properties))
            .recipe((context, provider) -> ShapelessRecipeBuilder.shapelessRecipe(context::get, 2)
                    .addIngredient(ItemTags.WOOL)
                    .addIngredient(Items.HONEYCOMB)
                    .addIngredient(Items.POPPY)
                    .addCriterion("has_item", RegistrateRecipeProvider.hasItem(Items.POPPY))
                    .build(provider)
            )
            .register();

    public static final ItemEntry<MedicalSuppliesItem> ADEQUATE_MEDICAL_SUPPLIES = IberiaRediscovered.getRegistrate()
            .object("adequate_medical_supplies")
            .item(properties -> new MedicalSuppliesItem(3, properties))
            .recipe((context, provider) -> ShapelessRecipeBuilder.shapelessRecipe(context::get, 6)
                    .addIngredient(Items.KELP)
                    .addIngredient(Items.HONEY_BOTTLE)
                    .addIngredient(Items.COCOA_BEANS)
                    .addIngredient(Items.CAKE)
                    .addCriterion("has_item", RegistrateRecipeProvider.hasItem(Items.CAKE))
                    .build(provider)
            )
            .register();

    public static final ItemEntry<MedicalSuppliesItem> SUPERNATURAL_MEDICAL_SUPPLIES = IberiaRediscovered.getRegistrate()
            .object("supernatural_medical_supplies")
            .item(properties -> new MedicalSuppliesItem(4, properties))
            .recipe((context, provider) -> ShapelessRecipeBuilder.shapelessRecipe(context::get, 8)
                    .addIngredient(Items.PHANTOM_MEMBRANE)
                    .addIngredient(Items.SPONGE)
                    .addIngredient(Items.POPPED_CHORUS_FRUIT)
                    .addIngredient(PotionIngredient.of(Potions.HEALING))
                    .addCriterion("has_item", RegistrateRecipeProvider.hasItem(Items.PHANTOM_MEMBRANE))
                    .build(provider)
            )
            .register();

    public static final ItemEntry<ElixirOfYouthItem> ELIXIR_OF_YOUTH = IberiaRediscovered.getRegistrate()
            .object("elixir_of_youth")
            .item(ElixirOfYouthItem::new)
            .register();

    public static void setup() {

    }
}
