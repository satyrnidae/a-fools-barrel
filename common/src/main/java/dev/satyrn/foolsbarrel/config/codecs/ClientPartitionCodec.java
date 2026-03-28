package dev.satyrn.foolsbarrel.config.codecs;

import dev.satyrn.foolsbarrel.api.config.BarrelOverlayMethod;
import dev.satyrn.foolsbarrel.api.config.NametagOptions;
import dev.satyrn.foolsbarrel.config.partitions.ClientPartition;
import dev.satyrn.lepidoptera.api.config.sync.ConfigCodec;
import net.minecraft.network.FriendlyByteBuf;

public final class ClientPartitionCodec implements ConfigCodec<ClientPartition> {
	public static final ClientPartitionCodec INSTANCE = new ClientPartitionCodec();

	private ClientPartitionCodec() {
	}

	@Override
	public void encode(final ClientPartition value, final FriendlyByteBuf buf) {
		value.writeToBuffer(buf);
	}

	@Override
	public ClientPartition decode(final FriendlyByteBuf buf) {
		final ClientPartition p = new ClientPartition();
		p.setAdjustCameraInBarrel(buf.readBoolean());
		p.setOverlayMethod(buf.readEnum(BarrelOverlayMethod.class));
		p.setHideNametag(buf.readEnum(NametagOptions.class));
		return p;
	}
}
