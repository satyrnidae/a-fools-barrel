package dev.satyrn.foolsbarrel.api.config;

import dev.satyrn.lepidoptera.config.NestingConfigData;
import net.minecraft.network.FriendlyByteBuf;
import org.apache.commons.lang3.NotImplementedException;

public interface CommonConfig<T extends CommonConfig<T>> extends NestingConfigData<T>, NetConfig {
	boolean getSnapHidingPlayersToGrid();
	boolean getShouldBarrelHideSightline();
	boolean getShouldAnimalsIgnoreHidingPlayers();
	boolean getShouldHidingRemoveMobAggro();
	boolean getAllowJumping();

	default void writeToBuffer(FriendlyByteBuf byteBuf) {
		byteBuf.writeBoolean(this.getSnapHidingPlayersToGrid());
		byteBuf.writeBoolean(this.getShouldBarrelHideSightline());
		byteBuf.writeBoolean(this.getShouldAnimalsIgnoreHidingPlayers());
		byteBuf.writeBoolean(this.getShouldHidingRemoveMobAggro());
		byteBuf.writeBoolean(this.getAllowJumping());
	}

	default void readFromBuffer(FriendlyByteBuf byteBuf) {
		throw new NotImplementedException();
	}
}
