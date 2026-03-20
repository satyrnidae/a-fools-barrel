package dev.satyrn.foolsbarrel.config.partitions;

import dev.satyrn.foolsbarrel.api.config.CommonConfig;
import dev.satyrn.lepidoptera.annotations.YamlComment;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

import java.beans.BeanProperty;
import java.beans.JavaBean;

@JavaBean
@Config(name = "common")
@YamlComment("""
	Common mod configuration
	These settings apply to both client and server sides
	Version 119.2.3""")
public class CommonPartition implements CommonConfig<CommonPartition> {
	@ConfigEntry.Gui.RequiresRestart(false) private boolean snapHidingPlayersToGrid = true;
	@ConfigEntry.Gui.RequiresRestart(false) private boolean allowJumping = true;
	@ConfigEntry.Gui.RequiresRestart(false) private boolean shouldBarrelHideSightline = true;
	@ConfigEntry.Gui.RequiresRestart(false) private boolean shouldAnimalsIgnoreHidingPlayers = true;
	@ConfigEntry.Gui.RequiresRestart(false) private boolean shouldHidingRemoveMobAggro = true;

	public CommonPartition() {
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
	public void copyFrom(final CommonPartition other) {
		this.snapHidingPlayersToGrid = other.snapHidingPlayersToGrid;
		this.shouldBarrelHideSightline = other.shouldBarrelHideSightline;
		this.allowJumping = other.allowJumping;
		this.shouldAnimalsIgnoreHidingPlayers = other.shouldAnimalsIgnoreHidingPlayers;
		this.shouldHidingRemoveMobAggro = other.shouldHidingRemoveMobAggro;
	}
}
