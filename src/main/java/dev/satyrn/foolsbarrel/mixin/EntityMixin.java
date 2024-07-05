package dev.satyrn.foolsbarrel.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.MovementFlag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Set;


@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract World getWorld();

    @Shadow
    public abstract float getPitch();

    @Shadow
    public abstract float getYaw();

    @Shadow
    public abstract boolean teleport(ServerWorld world, double x, double y, double z, Set<MovementFlag> flags, float yaw, float pitch);

    @Shadow
    public abstract boolean isInSneakingPose();

    @Shadow
    public abstract double getZ();

    @Shadow
    public abstract double getY();

    @Shadow
    public abstract double getX();

    @Shadow
    public abstract void setPosition(double x, double y, double z);

    @Shadow
    public abstract EntityPose getPose();

    @Shadow
    protected Box calculateBoundingBox() {
        return null;
    }
}
