package xyz.brassgoggledcoders.iberiarediscovered.event;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.GameType;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerSetSpawnEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import xyz.brassgoggledcoders.iberiarediscovered.api.capability.IPlayerInfo;
import xyz.brassgoggledcoders.iberiarediscovered.api.capability.WorldPos;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredBlockTags;
import xyz.brassgoggledcoders.iberiarediscovered.content.RediscoveredCapabilities;
import xyz.brassgoggledcoders.iberiarediscovered.module.Modules;

import java.util.Random;

public class RandomizedRebirthEventHandler {

    @SubscribeEvent
    public void onLoggedIn(PlayerEvent.PlayerLoggedInEvent playerLoggedInEvent) {
        World world = playerLoggedInEvent.getPlayer().getEntityWorld();
        if (!world.isRemote() && world instanceof ServerWorld) {
            IPlayerInfo playerInfo = playerLoggedInEvent.getPlayer()
                    .getCapability(RediscoveredCapabilities.PLAYER_INFO)
                    .resolve()
                    .orElse(null);

            if (playerInfo != null) {
                if (playerInfo.getOriginRebirth() == null && Modules.getRebirth().isActiveFor(playerInfo)) {
                    WorldPos originRebirth = this.generateOriginRebirth(playerLoggedInEvent.getPlayer(), (ServerWorld) world);
                    playerInfo.setOriginRebirth(originRebirth);
                    playerInfo.setLastRebirth(originRebirth);
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

    private WorldPos generateOriginRebirth(PlayerEntity playerEntity, ServerWorld world) {
        BlockPos worldSpawnPoint = world.getSpawnPoint();
        if (world.getDimensionType().hasSkyLight() && world.getServer().getServerConfiguration().getGameType() != GameType.ADVENTURE) {
            int maxSpawnRadius = Math.max(0, world.getServer().getSpawnRadius(world));
            int worldBorderDistance = MathHelper.floor(world.getWorldBorder().getClosestDistance(worldSpawnPoint.getX(), worldSpawnPoint.getZ()));
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
                        worldSpawnPoint.getX() + j2 - maxSpawnRadius,
                        worldSpawnPoint.getZ() + k2 - maxSpawnRadius
                );
                if (playerPosition != null) {
                    playerEntity.moveToBlockPosAndAngles(playerPosition, 0.0F, 0.0F);
                    if (world.hasNoCollisions(playerEntity)) {
                        break;
                    }
                }
            }
        } else {
            playerEntity.moveToBlockPosAndAngles(worldSpawnPoint, 0.0F, 0.0F);

            while (!world.hasNoCollisions(playerEntity) && playerEntity.getPosY() < 255.0D) {
                playerEntity.setPosition(playerEntity.getPosX(), playerEntity.getPosY() + 1.0D, playerEntity.getPosZ());
            }
        }
        return new WorldPos(playerEntity.getEntityWorld().getDimensionKey(), playerEntity.getPosition());
    }

    private int checkSize(int size) {
        return size <= 16 ? size - 1 : 17;
    }

    protected static BlockPos findValidPlayerPosition(ServerWorld p_241092_0_, int p_241092_1_, int p_241092_2_) {
        BlockPos.Mutable blockpos$mutable = new BlockPos.Mutable(p_241092_1_, 0, p_241092_2_);
        Biome biome = p_241092_0_.getBiome(blockpos$mutable);
        boolean flag = p_241092_0_.getDimensionType().getHasCeiling();
        BlockState blockstate = biome.getGenerationSettings().getSurfaceBuilderConfig().getTop();
        Chunk chunk = p_241092_0_.getChunk(p_241092_1_ >> 4, p_241092_2_ >> 4);
        int i = flag ? p_241092_0_.getChunkProvider().getChunkGenerator().getGroundHeight() : chunk.getTopBlockY(Heightmap.Type.MOTION_BLOCKING, p_241092_1_ & 15, p_241092_2_ & 15);
        if (i < 0) {
            return null;
        } else {
            int j = chunk.getTopBlockY(Heightmap.Type.WORLD_SURFACE, p_241092_1_ & 15, p_241092_2_ & 15);
            if (j <= i && j > chunk.getTopBlockY(Heightmap.Type.OCEAN_FLOOR, p_241092_1_ & 15, p_241092_2_ & 15)) {
                return null;
            } else {
                for (int k = i + 1; k >= 0; --k) {
                    blockpos$mutable.setPos(p_241092_1_, k, p_241092_2_);
                    BlockState blockstate1 = p_241092_0_.getBlockState(blockpos$mutable);
                    if (!blockstate1.getFluidState().isEmpty()) {
                        break;
                    }

                    if (blockstate1.equals(blockstate)) {
                        return blockpos$mutable.up().toImmutable();
                    }
                }

                return null;
            }
        }
    }
}
