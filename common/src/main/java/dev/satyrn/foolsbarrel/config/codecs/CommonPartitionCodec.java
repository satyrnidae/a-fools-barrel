package dev.satyrn.foolsbarrel.config.codecs;

import dev.satyrn.foolsbarrel.config.partitions.CommonPartition;
import dev.satyrn.lepidoptera.api.config.sync.ConfigCodec;
import net.minecraft.network.FriendlyByteBuf;

public final class CommonPartitionCodec implements ConfigCodec<CommonPartition> {
	public static final CommonPartitionCodec INSTANCE = new CommonPartitionCodec();

	private CommonPartitionCodec() {
	}

	@Override
	public void encode(final CommonPartition value, final FriendlyByteBuf buf) {
		value.writeToBuffer(buf);
	}

	@Override
	public CommonPartition decode(final FriendlyByteBuf buf) {
		final CommonPartition p = new CommonPartition();
		p.setSnapHidingPlayersToGrid(buf.readBoolean());
		p.setShouldBarrelHideSightline(buf.readBoolean());
		p.setShouldAnimalsIgnoreHidingPlayers(buf.readBoolean());
		p.setShouldHidingRemoveMobAggro(buf.readBoolean());
		p.setAllowJumping(buf.readBoolean());
		p.setAllowHidingPlayerInventory(buf.readBoolean());
		return p;
	}
}
