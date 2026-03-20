package dev.satyrn.foolsbarrel.api.config;

import dev.satyrn.lepidoptera.config.NestingConfigData;
import net.minecraft.network.FriendlyByteBuf;
import org.apache.commons.lang3.NotImplementedException;

public interface ClientConfig<T extends ClientConfig<T>> extends NestingConfigData<T>, NetConfig {
	boolean getAdjustCameraInBarrel();
	BarrelOverlayMethod getOverlayMethod();
	NametagOptions getHideNametag();

	@Override
	default void writeToBuffer(FriendlyByteBuf byteBuf) {
		byteBuf.writeBoolean(this.getAdjustCameraInBarrel());
		byteBuf.writeEnum(this.getOverlayMethod());
		byteBuf.writeEnum(this.getHideNametag());
	}

	default void readFromBuffer(FriendlyByteBuf byteBuf) {
		throw new NotImplementedException();
	}
}
