package dev.satyrn.foolsbarrel.api.extensions.world.entity.player;

import net.minecraft.core.Direction;

public interface PlayerExtensions {
	Direction getBarrelFacing();
	void setBarrelFacing(Direction facing);
	boolean isBarrelOpen();
	void setBarrelOpen(boolean open);
}
