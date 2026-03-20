package dev.satyrn.foolsbarrel.config.partitions;

import dev.satyrn.foolsbarrel.api.config.BarrelOverlayMethod;
import dev.satyrn.foolsbarrel.api.config.ClientConfig;
import dev.satyrn.foolsbarrel.api.config.NametagOptions;
import dev.satyrn.lepidoptera.annotations.YamlComment;

import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import org.jetbrains.annotations.Nullable;

import java.beans.BeanProperty;
import java.beans.JavaBean;
import java.util.Optional;

@JavaBean
@Config(name = "client")
@YamlComment("""
	Client-sided config
	These settings only apply to the client, and are ignored entirely by the server.
	Version 119.2.3""")
public class ClientPartition implements ClientConfig<ClientPartition> {
	@ConfigEntry.Gui.RequiresRestart(false) private boolean adjustCameraInBarrel = true;
	@ConfigEntry.Gui.RequiresRestart(false)
	@ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
	private BarrelOverlayMethod overlayMethod = BarrelOverlayMethod.PIN_VERTICALLY;
	@ConfigEntry.Gui.RequiresRestart(false)
	@ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
	private NametagOptions hideNametag = NametagOptions.WHEN_CROUCHED;

	public ClientPartition() {
	}

	@Override
	@BeanProperty
	@YamlComment(value = "Whether the camera height will be adjusted while a player is wearing a barrel.",
	             defaultValue = "true")
	public boolean getAdjustCameraInBarrel() {
		return this.adjustCameraInBarrel;
	}

	@SuppressWarnings("unused")
	public void setAdjustCameraInBarrel(final boolean value) {
		this.adjustCameraInBarrel = value;
	}

	@Override
	@BeanProperty
	@YamlComment(value = "Whether to show the barrel overlay image in first person.",
	             defaultValue = "PIN_VERTICAL")
	public BarrelOverlayMethod getOverlayMethod() {
		return this.overlayMethod;
	}

	@SuppressWarnings("unused")
	public void setOverlayMethod(final @Nullable BarrelOverlayMethod value) {
		this.overlayMethod = Optional.ofNullable(value).orElse(BarrelOverlayMethod.PIN_VERTICALLY);
	}

	@Override
	@BeanProperty
	@YamlComment(value = "Used to determine when a player's nametag should be rendered.",
	             defaultValue = "WHEN_CROUCHED")
	public NametagOptions getHideNametag() {
		return this.hideNametag;
	}

	@SuppressWarnings("unused")
	public void setHideNametag(final @Nullable NametagOptions value) {
		this.hideNametag = Optional.ofNullable(value).orElse(NametagOptions.WHEN_CROUCHED);
	}

	@Override
	public void copyFrom(final ClientPartition other) {
		this.overlayMethod = other.overlayMethod;
	}
}
