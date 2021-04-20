package xyz.brassgoggledcoders.iberiarediscovered.content;

import xyz.brassgoggledcoders.iberiarediscovered.IberiaRediscovered;
import xyz.brassgoggledcoders.iberiarediscovered.item.ElixirOfYouthItem;
import xyz.brassgoggledcoders.iberiarediscovered.item.MedicalSuppliesItem;
import com.tterrag.registrate.util.entry.ItemEntry;

public class RediscoveredItems {

    public static final ItemEntry<MedicalSuppliesItem> PRIMITIVE_MEDICAL_SUPPLIES = IberiaRediscovered.getRegistrate()
            .object("primitive_medical_supplies")
            .item(properties -> new MedicalSuppliesItem(1, properties))
            .register();

    public static final ItemEntry<MedicalSuppliesItem> BASIC_MEDICAL_SUPPLIES = IberiaRediscovered.getRegistrate()
            .object("basic_medical_supplies")
            .item(properties -> new MedicalSuppliesItem(2, properties))
            .register();

    public static final ItemEntry<MedicalSuppliesItem> ADEQUATE_MEDICAL_SUPPLIES = IberiaRediscovered.getRegistrate()
            .object("adequate_medical_supplies")
            .item(properties -> new MedicalSuppliesItem(3, properties))
            .register();

    public static final ItemEntry<MedicalSuppliesItem> SUPERNATURAL_MEDICAL_SUPPLIES = IberiaRediscovered.getRegistrate()
            .object("supernatural_medical_supplies")
            .item(properties -> new MedicalSuppliesItem(4, properties))
            .register();

    public static final ItemEntry<ElixirOfYouthItem> ELIXIR_OF_YOUTH = IberiaRediscovered.getRegistrate()
            .object("elixir_of_youth")
            .item(ElixirOfYouthItem::new)
            .register();

    public static void setup() {

    }
}
