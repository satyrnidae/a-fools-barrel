package dev.satyrn.foolsbarrel.api.config;

import dev.satyrn.lepidoptera.api.config.NestingConfigData;
import net.minecraft.network.FriendlyByteBuf;

public interface ClientConfig<T extends ClientConfig<T>> extends NestingConfigData<T> {
	boolean getAdjustCameraInBarrel();
	BarrelOverlayMethod getOverlayMethod();
	NametagOptions getHideNametag();

	default void writeToBuffer(FriendlyByteBuf byteBuf) {
		byteBuf.writeBoolean(this.getAdjustCameraInBarrel());
		byteBuf.writeEnum(this.getOverlayMethod());
		byteBuf.writeEnum(this.getHideNametag());
	}
}
