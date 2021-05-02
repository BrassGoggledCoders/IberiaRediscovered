package xyz.brassgoggledcoders.iberiarediscovered.item;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import xyz.brassgoggledcoders.iberiarediscovered.entity.BasicTeleporter;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TeamTokenItem extends Item {
    public TeamTokenItem(Properties properties) {
        super(properties);
    }

    @Override
    @Nonnull
    @ParametersAreNonnullByDefault
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity player, Hand hand) {
        ItemStack itemStack = player.getHeldItem(hand);
        boolean teleported = false;
        if (player.getTeam() != null) {
            Team team = player.getTeam();
            if (world.isRemote()) {
                return ActionResult.resultConsume(itemStack);
            } else if (world instanceof ServerWorld) {
                ServerWorld serverWorld = (ServerWorld) world;
                MinecraftServer minecraftServer = serverWorld.getServer();
                List<ServerPlayerEntity> playerEntityList = new ArrayList<>(minecraftServer.getPlayerList().getPlayers());
                playerEntityList.removeIf(serverPlayerEntity -> !team.isSameTeam(serverPlayerEntity.getTeam()) ||
                        serverPlayerEntity == player);
                if (playerEntityList.isEmpty()) {
                    Optional<Entity> teammate = serverWorld.getEntities()
                            .filter(entity -> team.isSameTeam(entity.getTeam()) && entity != player)
                            .findAny();
                    teleported = teammate.map(entity -> {
                        teleport(serverWorld, player, entity);
                        return true;
                    }).orElse(false);
                } else if (playerEntityList.size() == 1) {
                    teleport(serverWorld, player, playerEntityList.get(0));
                    itemStack.shrink(1);
                } else {
                    teleport(serverWorld, player, playerEntityList.get(world.getRandom().nextInt(playerEntityList.size())));
                }
            }
        }
        if (teleported) {
            itemStack.shrink(1);
            player.getCooldownTracker().setCooldown(this, 20 * 5);
            return ActionResult.resultSuccess(itemStack);
        } else {
            player.getCooldownTracker().setCooldown(this, 20 * 60);
            return ActionResult.resultFail(itemStack);
        }
    }

    private void teleport(ServerWorld world, PlayerEntity user, Entity target) {
        if (user.getEntityWorld() == target.getEntityWorld()) {
            Vector3d targetVec = target.getPositionVec();
            user.setPositionAndUpdate(targetVec.getX(), targetVec.getY(), targetVec.getZ());
        } else {
            user.changeDimension(world, new BasicTeleporter(target.getPositionVec()));
        }
    }
}
