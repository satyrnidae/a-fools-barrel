package dev.satyrn.foolsbarrel.config.partitions;

import dev.satyrn.foolsbarrel.api.config.ServerConfig;
import dev.satyrn.lepidoptera.annotations.YamlComment;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import javax.annotation.Nullable;
import java.beans.BeanProperty;
import java.beans.JavaBean;

@JavaBean
@Config(name = "server")
public class ServerPartition implements ServerConfig<ServerPartition> {
	@ConfigEntry.Gui.RequiresRestart(false) private boolean snapHidingPlayersToGrid = true;
	@ConfigEntry.Gui.RequiresRestart(false) private boolean allowJumping = true;
	@ConfigEntry.Gui.RequiresRestart(false) private boolean shouldBarrelHideSightline = true;
	@ConfigEntry.Gui.RequiresRestart(false) private boolean shouldAnimalsIgnoreHidingPlayers = true;
	@ConfigEntry.Gui.RequiresRestart(false) private boolean shouldHidingRemoveMobAggro = true;
	@ConfigEntry.Gui.RequiresRestart(false) private boolean shouldOverrideClientConfig = true;
	@ConfigEntry.Gui.TransitiveObject
	@ConfigEntry.Category("clientOverrides")
	private final ClientPartition clientOverrides = new ClientPartition();

	public ServerPartition() {
	}

	@Override
	@BeanProperty
	@YamlComment(value = "Whether players should snap to the voxel grid when they crouch in a barrel.", note = "When combined with allowJumping=true, this may allow players to move around while hiding.", defaultValue = "true")
	public boolean getSnapHidingPlayersToGrid() {
		return this.snapHidingPlayersToGrid;
	}

	@SuppressWarnings("unused")
	public void setSnapHidingPlayersToGrid(final boolean value) {
		this.snapHidingPlayersToGrid = false;
	}

	@Override
	@BeanProperty
	@YamlComment(value = "Whether Endermen should ignore players who look directly at them when they are wearing a barrel.", defaultValue = "true")
	public boolean getShouldBarrelHideSightline() {
		return this.shouldBarrelHideSightline;
	}

	@SuppressWarnings("unused")
	public void setShouldBarrelHideSightline(final boolean value) {
		this.shouldBarrelHideSightline = value;
	}

	@Override
	@BeanProperty
	@YamlComment(value = "Whether mobs which occasionally look at the player should ignore them entirely while they are hidden.", defaultValue = "true")
	public boolean getShouldAnimalsIgnoreHidingPlayers() {
		return this.shouldAnimalsIgnoreHidingPlayers;
	}

	@SuppressWarnings("unused")
	public void setShouldAnimalsIgnoreHidingPlayers(final boolean value) {
		this.shouldAnimalsIgnoreHidingPlayers = value;
	}

	@Override
	@BeanProperty
	@YamlComment(value = "Whether attacking mobs should be \"forgetful\", i.e. if they should stop attacking immediately when the player hides.", defaultValue = "true", note="This makes mobs act incredibly stupid, which matched the goofiness of the original snapshot, but may not be desired here.")
	public boolean getShouldHidingRemoveMobAggro() {
		return this.shouldHidingRemoveMobAggro;
	}

	@SuppressWarnings("unused")
	public void setShouldHidingRemoveMobAggro(final boolean value) {
		this.shouldHidingRemoveMobAggro = value;
	}

	@Override
	@BeanProperty
	@YamlComment(value = "Whether players who are hiding in barrels should be allowed to jump.", note = "When combined with snapHidingPlayersToGrid=false, this may allow players to slowly move while hiding.", defaultValue = "true")
	public boolean getAllowJumping() {
		return this.allowJumping;
	}

	@SuppressWarnings("unused")
	public void setAllowJumping(final boolean value) {
		this.allowJumping = value;
	}

	@Override
	@BeanProperty
	@YamlComment(value = "Whether the server should override connected clients' client-side config.", note = "Server side config is always synced, as not doing so would lead to player movement desync.", defaultValue = "true")
	public boolean getShouldOverrideClientConfig() {
		return this.shouldOverrideClientConfig;
	}

	@SuppressWarnings("unused")
	public void setShouldOverrideClientConfig(final boolean value) {
		this.shouldOverrideClientConfig = value;
	}

	@Override
	@BeanProperty
	@YamlComment(value = "Client-specific values that the server will override on clients.", emitType = false)
	public ClientPartition getClientOverrides() {
		return this.clientOverrides;
	}

	@SuppressWarnings("unused")
	public void setClientOverrides(final @Nullable ClientPartition value) {
		this.clientOverrides.copyFrom(value == null ? new ClientPartition() : value);
	}

	@Override
	public void copyFrom(ServerPartition other) {
		this.snapHidingPlayersToGrid = other.snapHidingPlayersToGrid;
		this.shouldBarrelHideSightline = other.shouldBarrelHideSightline;
		this.allowJumping = other.allowJumping;
		this.shouldAnimalsIgnoreHidingPlayers = other.shouldAnimalsIgnoreHidingPlayers;
		this.shouldHidingRemoveMobAggro = other.shouldHidingRemoveMobAggro;
		this.shouldOverrideClientConfig = other.shouldOverrideClientConfig;
		this.setClientOverrides(other.getClientOverrides());
	}
}
