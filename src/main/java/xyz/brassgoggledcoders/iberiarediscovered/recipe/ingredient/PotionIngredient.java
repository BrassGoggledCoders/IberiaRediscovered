package xyz.brassgoggledcoders.iberiarediscovered.recipe.ingredient;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ResourceLocation;
import xyz.brassgoggledcoders.iberiarediscovered.IberiaRediscovered;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.stream.Stream;

public class PotionIngredient extends Ingredient {
    public static final ResourceLocation NAME = IberiaRediscovered.rl("potion");

    private final Potion potion;

    protected PotionIngredient(Potion potion) {
        super(Stream.of(new SingleItemList(createPotion(potion))));
        this.potion = potion;
    }

    @Override
    public boolean test(@Nullable ItemStack itemStack) {
        if (itemStack != null && !itemStack.isEmpty()) {
            Potion testPotion = PotionUtils.getPotionFromItem(itemStack);
            return testPotion == potion;
        }
        return false;
    }

    @Nonnull
    public JsonElement serialize() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", NAME.toString());
        jsonObject.addProperty("potion", Objects.requireNonNull(this.getPotion().getRegistryName()).toString());
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
        PotionUtils.addPotionToItemStack(itemStack, potion);
        return itemStack;
    }
}
