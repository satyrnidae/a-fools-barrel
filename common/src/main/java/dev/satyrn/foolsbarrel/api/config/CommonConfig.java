package dev.satyrn.foolsbarrel.api.config;

import dev.satyrn.lepidoptera.api.config.NestingConfigData;
import net.minecraft.network.FriendlyByteBuf;

public interface CommonConfig<T extends CommonConfig<T>> extends NestingConfigData<T> {
	boolean getSnapHidingPlayersToGrid();
	boolean getShouldBarrelHideSightline();
	boolean getShouldAnimalsIgnoreHidingPlayers();
	boolean getShouldHidingRemoveMobAggro();
	boolean getAllowJumping();
	boolean getAllowHidingPlayerInventory();

	default void writeToBuffer(FriendlyByteBuf byteBuf) {
		byteBuf.writeBoolean(this.getSnapHidingPlayersToGrid());
		byteBuf.writeBoolean(this.getShouldBarrelHideSightline());
		byteBuf.writeBoolean(this.getShouldAnimalsIgnoreHidingPlayers());
		byteBuf.writeBoolean(this.getShouldHidingRemoveMobAggro());
		byteBuf.writeBoolean(this.getAllowJumping());
		byteBuf.writeBoolean(this.getAllowHidingPlayerInventory());
	}
}
