package xyz.brassgoggledcoders.iberiarediscovered.content;

import net.minecraft.network.chat.Component;
import xyz.brassgoggledcoders.iberiarediscovered.IberiaRediscovered;

public class RediscoveredTexts {

    public static final Component NOT_PLAYER_CHOICE = IberiaRediscovered.getRegistrate()
            .addLang("command", IberiaRediscovered.rl("not_player_choice"), "This Module does not allow Players to Choose");

    public static void setup() {

    }
}
