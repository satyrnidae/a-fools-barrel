package dev.satyrn.foolsbarrel.api.config;

import net.minecraft.network.FriendlyByteBuf;

public interface NetConfig {
	void writeToBuffer(FriendlyByteBuf byteBuf);
	void readFromBuffer(FriendlyByteBuf byteBuf);
}
