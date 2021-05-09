package xyz.brassgoggledcoders.iberiarediscovered.module;

import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.common.MinecraftForge;
import org.apache.commons.lang3.tuple.Pair;
import xyz.brassgoggledcoders.iberiarediscovered.api.capability.IPlayerInfo;
import xyz.brassgoggledcoders.iberiarediscovered.config.RebirthCenter;
import xyz.brassgoggledcoders.iberiarediscovered.event.RandomizedRebirthEventHandler;

public class RandomizedRebirthModule extends Module {
    public static final String NAME = "randomized_rebirth";

    private Pair<String, RegistryKey<World>> cachedOriginWorld;
    private ConfigValue<String> originWorld;
    private IntValue originDistance;
    private IntValue rebirthDistance;
    private ConfigValue<RebirthCenter> rebirthCenter;

    public RandomizedRebirthModule() {
        super(NAME);
        MinecraftForge.EVENT_BUS.register(new RandomizedRebirthEventHandler(this));
    }

    @Override
    public void configureServer(ForgeConfigSpec.Builder builder) {
        super.configureServer(builder);
        originWorld = builder.define(
                "originWorld",
                World.THE_NETHER.getLocation().toString(),
                value -> value != null && ResourceLocation.tryCreate(value.toString()) != null
        );
        originDistance = builder.defineInRange("originDistance", 1000, 10, Integer.MAX_VALUE);
        rebirthDistance = builder.defineInRange("rebirthDistance", 1000, 10, Integer.MAX_VALUE);
        rebirthCenter = builder.defineEnum("rebirthCenter", RebirthCenter.LAST_REBIRTH);
    }

    public int getOriginDistance() {
        return this.originDistance.get();
    }

    public int getRebirthDistance() {
        return this.rebirthDistance.get();
    }

    public GlobalPos getRebirthCenter(GlobalPos spawn, IPlayerInfo playerInfo) {
        return this.rebirthCenter.get().getPosition(spawn, playerInfo);
    }

    public RegistryKey<World> getOriginWorld() {
        if (cachedOriginWorld == null || !cachedOriginWorld.getLeft().equalsIgnoreCase(this.originWorld.get())) {
            cachedOriginWorld = Pair.of(
                    this.originWorld.get(),
                    RegistryKey.getOrCreateKey(Registry.WORLD_KEY, new ResourceLocation(this.originWorld.get()))
            );
        }
        return cachedOriginWorld.getRight();
    }
}
