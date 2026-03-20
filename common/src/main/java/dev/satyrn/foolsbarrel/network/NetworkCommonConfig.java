package dev.satyrn.foolsbarrel.network;

import dev.satyrn.foolsbarrel.api.config.CommonConfig;
import net.minecraft.network.FriendlyByteBuf;

public class NetworkCommonConfig implements CommonConfig<NetworkCommonConfig> {
	private boolean snapHidingPlayersToGrid;
	private boolean shouldBarrelHideSightline;
	private boolean shouldAnimalsIgnoreHidingPlayers;
	private boolean shouldHidingRemoveMobAggro;
	private boolean allowJumping;

	NetworkCommonConfig(FriendlyByteBuf byteBuf) {
		this.readFromBuffer(byteBuf);
	}

	@Override
	public boolean getSnapHidingPlayersToGrid() {
		return this.snapHidingPlayersToGrid;
	}

	@Override
	public boolean getShouldBarrelHideSightline() {
		return this.shouldBarrelHideSightline;
	}

	@Override
	public boolean getShouldAnimalsIgnoreHidingPlayers() {
		return this.shouldAnimalsIgnoreHidingPlayers;
	}

	@Override
	public boolean getShouldHidingRemoveMobAggro() {
		return this.shouldHidingRemoveMobAggro;
	}

	@Override
	public boolean getAllowJumping() {
		return this.allowJumping;
	}

	@Override
	public void readFromBuffer(FriendlyByteBuf byteBuf) {
		this.snapHidingPlayersToGrid = byteBuf.readBoolean();
		this.shouldBarrelHideSightline = byteBuf.readBoolean();
		this.shouldAnimalsIgnoreHidingPlayers = byteBuf.readBoolean();
		this.shouldHidingRemoveMobAggro = byteBuf.readBoolean();
		this.allowJumping = byteBuf.readBoolean();
	}

	@Override
	public void copyFrom(NetworkCommonConfig other) {
		this.snapHidingPlayersToGrid = other.snapHidingPlayersToGrid;
		this.shouldBarrelHideSightline = other.shouldBarrelHideSightline;
		this.shouldAnimalsIgnoreHidingPlayers = other.shouldAnimalsIgnoreHidingPlayers;
		this.shouldHidingRemoveMobAggro = other.shouldHidingRemoveMobAggro;
		this.allowJumping = other.allowJumping;
	}
}
