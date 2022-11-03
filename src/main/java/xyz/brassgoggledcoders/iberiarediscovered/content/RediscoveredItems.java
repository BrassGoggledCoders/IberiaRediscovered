package xyz.brassgoggledcoders.iberiarediscovered.content;

import com.tterrag.registrate.providers.RegistrateRecipeProvider;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
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
            .recipe((context, provider) -> ShapelessRecipeBuilder.shapeless(context::get)
                    .requires(Tags.Items.EGGS)
                    .requires(Items.BOWL)
                    .requires(Items.SWEET_BERRIES)
                    .unlockedBy("has_item", RegistrateRecipeProvider.has(Items.SWEET_BERRIES))
                    .save(provider)
            )
            .register();

    public static final ItemEntry<MedicalSuppliesItem> BASIC_MEDICAL_SUPPLIES = IberiaRediscovered.getRegistrate()
            .object("basic_medical_supplies")
            .item(properties -> new MedicalSuppliesItem(2, properties))
            .recipe((context, provider) -> ShapelessRecipeBuilder.shapeless(context::get, 2)
                    .requires(ItemTags.WOOL)
                    .requires(Items.HONEYCOMB)
                    .requires(Items.POPPY)
                    .unlockedBy("has_item", RegistrateRecipeProvider.has(Items.POPPY))
                    .save(provider)
            )
            .register();

    public static final ItemEntry<MedicalSuppliesItem> ADEQUATE_MEDICAL_SUPPLIES = IberiaRediscovered.getRegistrate()
            .object("adequate_medical_supplies")
            .item(properties -> new MedicalSuppliesItem(3, properties))
            .recipe((context, provider) -> ShapelessRecipeBuilder.shapeless(context::get, 6)
                    .requires(Items.KELP)
                    .requires(Items.HONEY_BOTTLE)
                    .requires(Items.COCOA_BEANS)
                    .requires(Items.CAKE)
                    .unlockedBy("has_item", RegistrateRecipeProvider.has(Items.CAKE))
                    .save(provider)
            )
            .register();

    public static final ItemEntry<MedicalSuppliesItem> SUPERNATURAL_MEDICAL_SUPPLIES = IberiaRediscovered.getRegistrate()
            .object("supernatural_medical_supplies")
            .item(properties -> new MedicalSuppliesItem(4, properties))
            .recipe((context, provider) -> ShapelessRecipeBuilder.shapeless(context::get, 8)
                    .requires(Items.PHANTOM_MEMBRANE)
                    .requires(Items.SPONGE)
                    .requires(Items.POPPED_CHORUS_FRUIT)
                    .requires(PotionIngredient.of(Potions.HEALING))
                    .unlockedBy("has_item", RegistrateRecipeProvider.has(Items.PHANTOM_MEMBRANE))
                    .save(provider)
            )
            .register();

    public static final ItemEntry<ElixirOfYouthItem> ELIXIR_OF_YOUTH = IberiaRediscovered.getRegistrate()
            .object("elixir_of_youth")
            .item(ElixirOfYouthItem::new)
            .register();

    public static void setup() {

    }
}
