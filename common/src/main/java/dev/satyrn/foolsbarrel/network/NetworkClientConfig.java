package dev.satyrn.foolsbarrel.network;

import dev.satyrn.foolsbarrel.api.config.BarrelOverlayMethod;
import dev.satyrn.foolsbarrel.api.config.ClientConfig;
import dev.satyrn.foolsbarrel.api.config.NametagOptions;
import net.minecraft.network.FriendlyByteBuf;

public class NetworkClientConfig implements ClientConfig<NetworkClientConfig> {
	private boolean adjustCameraInBarrel;
	private BarrelOverlayMethod overlayMethod;
	private NametagOptions nametagOptions;

	NetworkClientConfig(FriendlyByteBuf byteBuf) {
		this.readFromBuffer(byteBuf);
	}

	@Override
	public boolean getAdjustCameraInBarrel() {
		return this.adjustCameraInBarrel;
	}

	@Override
	public BarrelOverlayMethod getOverlayMethod() {
		return this.overlayMethod;
	}

	@Override
	public NametagOptions getHideNametag() {
		return this.nametagOptions;
	}

	@Override
	public void readFromBuffer(FriendlyByteBuf byteBuf) {
		this.adjustCameraInBarrel = byteBuf.readBoolean();
		this.overlayMethod = byteBuf.readEnum(BarrelOverlayMethod.class);
		this.nametagOptions = byteBuf.readEnum(NametagOptions.class);
	}

	@Override
	public void copyFrom(NetworkClientConfig other) {

	}
}
