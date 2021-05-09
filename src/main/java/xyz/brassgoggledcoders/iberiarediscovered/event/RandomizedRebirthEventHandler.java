package xyz.brassgoggledcoders.iberiarediscovered.event;

import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import xyz.brassgoggledcoders.iberiarediscovered.IberiaRediscovered;
import xyz.brassgoggledcoders.iberiarediscovered.api.capability.IPlayerInfo;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredBlockTags;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredCapabilities;
import xyz.brassgoggledcoders.iberiarediscovered.module.Modules;
import xyz.brassgoggledcoders.iberiarediscovered.module.RandomizedRebirthModule;

import java.util.Random;

public class RandomizedRebirthEventHandler {
    private final RandomizedRebirthModule module;

    public RandomizedRebirthEventHandler(RandomizedRebirthModule module) {
        this.module = module;
    }

    @SubscribeEvent
    public void onLoggedIn(PlayerEvent.PlayerLoggedInEvent playerLoggedInEvent) {
        World world = playerLoggedInEvent.getPlayer().getEntityWorld();
        if (!world.isRemote() && world instanceof ServerWorld) {
            ServerWorld serverWorld = (ServerWorld) world;
            IPlayerInfo playerInfo = playerLoggedInEvent.getPlayer()
                    .getCapability(RediscoveredCapabilities.PLAYER_INFO)
                    .resolve()
                    .orElse(null);

            if (playerInfo != null) {
                if (playerInfo.getLastRebirth() == null && module.isActiveFor(playerInfo)) {
                    playerInfo.setLastRebirth(this.generateRebirth(
                            serverWorld.getServer(),
                            playerLoggedInEvent.getPlayer(),
                            GlobalPos.getPosition(module.getOriginWorld(), serverWorld.getSpawnPoint()),
                            module.getOriginDistance()
                    ));
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerClone(PlayerEvent.Clone playerEvent) {
        if (playerEvent.isWasDeath()) {
            playerEvent.getOriginal()
                    .getCapability(RediscoveredCapabilities.PLAYER_INFO)
                    .resolve()
                    .map(INBTSerializable::serializeNBT)
                    .ifPresent(original -> playerEvent.getPlayer()
                            .getCapability(RediscoveredCapabilities.PLAYER_INFO)
                            .ifPresent(clone -> clone.deserializeNBT(original))
                    );
        }
    }

    @SubscribeEvent
    public void onPlayerRespawn(PlayerRespawnEvent playerRespawnEvent) {
        if (!playerRespawnEvent.isEndConquered()) {
            PlayerEntity playerEntity = playerRespawnEvent.getPlayer();
            World world = playerEntity.getEntityWorld();
            if (world instanceof ServerWorld && playerEntity instanceof ServerPlayerEntity) {
                IPlayerInfo playerInfo = playerEntity.getCapability(RediscoveredCapabilities.PLAYER_INFO)
                        .resolve()
                        .orElse(null);
                ServerPlayerEntity serverPlayerEntity = ((ServerPlayerEntity) playerEntity);
                ServerWorld serverWorld = (ServerWorld) world;
                BlockPos respawnPos = serverPlayerEntity.func_241140_K_();
                ServerWorld respawnWorld = serverWorld.getServer().getWorld(serverPlayerEntity.func_241141_L_());
                if (playerInfo != null && Modules.getRebirth().isActiveFor(playerInfo)) {
                    GlobalPos spawnGlobal = GlobalPos.getPosition(module.getOriginWorld(), serverWorld.getSpawnPoint());
                    GlobalPos respawnCenter = module.getRebirthCenter(spawnGlobal, playerInfo);
                    if (respawnPos != null && respawnWorld != null) {
                        BlockState blockState = respawnWorld.getBlockState(respawnPos);
                        if (!blockState.isIn(RediscoveredBlockTags.REBIRTH_VALID)) {
                            playerInfo.setLastRebirth(this.generateRebirth(
                                    serverWorld.getServer(),
                                    playerEntity,
                                    respawnCenter,
                                    Modules.RANDOMIZED_SPAWN_MODULE.getRebirthDistance()
                            ));
                        }
                    } else {
                        playerInfo.setLastRebirth(this.generateRebirth(
                                serverWorld.getServer(),
                                playerEntity,
                                respawnCenter,
                                Modules.RANDOMIZED_SPAWN_MODULE.getRebirthDistance()
                        ));
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerSetSpawn(PlayerSetSpawnEvent playerSetSpawnEvent) {
        PlayerEntity player = playerSetSpawnEvent.getPlayer();
        if (!playerSetSpawnEvent.isForced() && playerSetSpawnEvent.getNewSpawn() != null &&
                !player.getEntityWorld().isRemote() && Modules.getRebirth().isActiveFor(player)) {
            MinecraftServer server = player.getServer();
            if (server != null) {
                ServerWorld world = server.getWorld(playerSetSpawnEvent.getSpawnWorld());
                if (world != null) {
                    BlockState blockState = world.getBlockState(playerSetSpawnEvent.getNewSpawn());
                    if (!blockState.isIn(RediscoveredBlockTags.REBIRTH_VALID)) {
                        playerSetSpawnEvent.setCanceled(true);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerDeath(LivingDeathEvent livingDeathEvent) {
        LivingEntity livingEntity = livingDeathEvent.getEntityLiving();
        if (livingEntity instanceof PlayerEntity) {
            GlobalPos deathPos = GlobalPos.getPosition(
                    livingEntity.getEntityWorld().getDimensionKey(),
                    livingEntity.getPosition()
            );
            livingEntity.getCapability(RediscoveredCapabilities.PLAYER_INFO)
                    .ifPresent(playerInfo -> playerInfo.setLastDeath(deathPos));
        }
    }

    private GlobalPos generateRebirth(MinecraftServer minecraftServer, PlayerEntity playerEntity, GlobalPos respawnCenter, int respawnRadius) {
        ServerWorld world = minecraftServer.getWorld(respawnCenter.getDimension());
        BlockPos centerPos;
        if (world == null) {
            IberiaRediscovered.LOGGER.warn("Failed to find World for {}", respawnCenter.getDimension());
            world = minecraftServer.func_241755_D_();
            centerPos = world.getSpawnPoint();
        } else {
            centerPos = respawnCenter.getPos();
        }
        if (world.getDimensionType().hasSkyLight()) {
            int maxSpawnRadius = Math.max(0, respawnRadius);
            int worldBorderDistance = MathHelper.floor(world.getWorldBorder().getClosestDistance(centerPos.getX(), centerPos.getZ()));
            if (worldBorderDistance < maxSpawnRadius) {
                maxSpawnRadius = worldBorderDistance;
            }

            if (worldBorderDistance <= 1) {
                maxSpawnRadius = 1;
            }

            long k = maxSpawnRadius * 2L + 1;
            long l = k * k;
            int i1 = l > 2147483647L ? Integer.MAX_VALUE : (int) l;
            int j1 = this.checkSize(i1);
            int k1 = (new Random()).nextInt(i1);

            for (int l1 = 0; l1 < i1; ++l1) {
                int i2 = (k1 + j1 * l1) % i1;
                int j2 = i2 % (maxSpawnRadius * 2 + 1);
                int k2 = i2 / (maxSpawnRadius * 2 + 1);
                BlockPos playerPosition = findValidPlayerPosition(
                        world,
                        centerPos.getX() + j2 - maxSpawnRadius,
                        centerPos.getZ() + k2 - maxSpawnRadius
                );
                if (playerPosition != null) {
                    playerEntity.moveToBlockPosAndAngles(playerPosition, 0.0F, 0.0F);
                    if (world.hasNoCollisions(playerEntity)) {
                        break;
                    }
                }
            }
        } else {
            playerEntity.moveToBlockPosAndAngles(centerPos, 0.0F, 0.0F);

            while (!world.hasNoCollisions(playerEntity) && playerEntity.getPosY() < 255.0D) {
                playerEntity.setPosition(playerEntity.getPosX(), playerEntity.getPosY() + 1.0D, playerEntity.getPosZ());
            }
        }
        playerEntity.setPositionAndUpdate(playerEntity.getPosX(), playerEntity.getPosY(), playerEntity.getPosZ());
        return GlobalPos.getPosition(playerEntity.getEntityWorld().getDimensionKey(), playerEntity.getPosition());
    }

    private int checkSize(int size) {
        return size <= 16 ? size - 1 : 17;
    }

    protected static BlockPos findValidPlayerPosition(ServerWorld world, int p_241092_1_, int p_241092_2_) {
        BlockPos.Mutable mutableBlockPos = new BlockPos.Mutable(p_241092_1_, 0, p_241092_2_);
        Biome biome = world.getBiome(mutableBlockPos);
        boolean flag = world.getDimensionType().getHasCeiling();
        BlockState blockstate = biome.getGenerationSettings().getSurfaceBuilderConfig().getTop();
        Chunk chunk = world.getChunk(p_241092_1_ >> 4, p_241092_2_ >> 4);
        int i = flag ? world.getChunkProvider().getChunkGenerator().getGroundHeight() :
                chunk.getTopBlockY(Heightmap.Type.MOTION_BLOCKING, p_241092_1_ & 15, p_241092_2_ & 15);
        if (i >= 0) {
            int j = chunk.getTopBlockY(Heightmap.Type.WORLD_SURFACE, p_241092_1_ & 15, p_241092_2_ & 15);
            if (j > i || j <= chunk.getTopBlockY(Heightmap.Type.OCEAN_FLOOR, p_241092_1_ & 15, p_241092_2_ & 15)) {
                for (int k = i + 1; k >= 0; --k) {
                    mutableBlockPos.setPos(p_241092_1_, k, p_241092_2_);
                    BlockState blockstate1 = world.getBlockState(mutableBlockPos);
                    if (!blockstate1.getFluidState().isEmpty()) {
                        break;
                    }

                    if (blockstate1.equals(blockstate)) {
                        return mutableBlockPos.up().toImmutable();
                    }
                }

            }
        }
        return null;
    }
}
