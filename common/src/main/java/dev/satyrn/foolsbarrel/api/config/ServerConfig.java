package dev.satyrn.foolsbarrel.api.config;

public interface ServerConfig<T extends CommonConfig<T>> extends CommonConfig<T> {
	boolean getShouldOverrideClientConfig();
	ClientConfig<?> getClientOverrides();
}
