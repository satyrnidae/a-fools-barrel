package dev.satyrn.foolsbarrel.mixin.world.entity.player;

import dev.satyrn.foolsbarrel.api.extensions.world.entity.player.PlayerExtensions;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
@Implements(@Interface(iface = PlayerExtensions.class, prefix = "foolsBarrelX$"))
public abstract class PlayerTrackedDataMixin {

	@Unique private static final EntityDataAccessor<Direction> BARREL_FACING =
		SynchedEntityData.defineId(Player.class, EntityDataSerializers.DIRECTION);

	@Unique private static final EntityDataAccessor<Boolean> BARREL_OPEN =
		SynchedEntityData.defineId(Player.class, EntityDataSerializers.BOOLEAN);

	@Inject(method = "defineSynchedData", at = @At("TAIL"))
	private void foolsBarrel$defineSynchedData(final SynchedEntityData.Builder builder, final CallbackInfo ci) {
		builder.define(BARREL_FACING, Direction.UP);
		builder.define(BARREL_OPEN, false);
	}

	@Unique
	public Direction foolsBarrelX$getBarrelFacing() {
		return ((Entity) (Object) this).getEntityData().get(BARREL_FACING);
	}

	@Unique
	public void foolsBarrelX$setBarrelFacing(final Direction facing) {
		((Entity) (Object) this).getEntityData().set(BARREL_FACING, facing);
	}

	@Unique
	public boolean foolsBarrelX$isBarrelOpen() {
		return ((Entity) (Object) this).getEntityData().get(BARREL_OPEN);
	}

	@Unique
	public void foolsBarrelX$setBarrelOpen(final boolean open) {
		((Entity) (Object) this).getEntityData().set(BARREL_OPEN, open);
	}
}
