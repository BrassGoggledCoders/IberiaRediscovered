package xyz.brassgoggledcoders.iberiarediscovered.recipe.ingredient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.registries.ForgeRegistries;
import xyz.brassgoggledcoders.iberiarediscovered.IberiaRediscovered;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.stream.Stream;

public class PotionIngredient extends Ingredient {
    public static final ResourceLocation NAME = IberiaRediscovered.rl("potion");

    private final Potion potion;

    protected PotionIngredient(Potion potion) {
        super(Stream.of(new ItemValue(createPotion(potion))));
        this.potion = potion;
    }

    @Override
    public boolean test(@Nullable ItemStack itemStack) {
        if (itemStack != null && !itemStack.isEmpty()) {
            Potion testPotion = PotionUtils.getPotion(itemStack);
            return testPotion == potion;
        }
        return false;
    }

    @Nonnull
    @Override
    public JsonElement toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", NAME.toString());
        jsonObject.addProperty("potion", Objects.requireNonNull(ForgeRegistries.POTIONS.getKey(potion)).toString());
        return jsonObject;
    }

    public Potion getPotion() {
        return potion;
    }

    public static PotionIngredient of(Potion potion) {
        return new PotionIngredient(potion);
    }

    private static ItemStack createPotion(Potion potion) {
        ItemStack itemStack = new ItemStack(Items.POTION);
        PotionUtils.setPotion(itemStack, potion);
        return itemStack;
    }
}
