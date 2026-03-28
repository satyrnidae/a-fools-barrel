package dev.satyrn.foolsbarrel.api.config;

import dev.satyrn.lepidoptera.api.config.NestingConfigData;
import net.minecraft.network.FriendlyByteBuf;

public interface ServerConfig<T extends CommonConfig<T>> extends CommonConfig<T> {
	boolean getShouldOverrideClientConfig();
	ClientConfig<?> getClientOverrides();
}
