package xyz.brassgoggledcoders.iberiarediscovered.entity;

import net.minecraft.block.PortalInfo;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;

import java.util.function.Function;

public class BasicTeleporter implements ITeleporter {
    private final Vector3d targetPosition;

    public BasicTeleporter(Vector3d targetPosition) {
        this.targetPosition = targetPosition;
    }

    @Override
    public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
        return repositionEntity.apply(false);
    }

    @Override
    public PortalInfo getPortalInfo(Entity entity, ServerWorld destWorld, Function<ServerWorld, PortalInfo> defaultPortalInfo) {
        return new PortalInfo(targetPosition, entity.getMotion(), entity.rotationYaw, entity.rotationPitch);
    }
}
