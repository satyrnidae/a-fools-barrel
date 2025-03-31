package dev.satyrn.foolsbarrel.fabriclike.client;

import dev.satyrn.foolsbarrel.client.FoolsBarrelClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class FoolsBarrelFabricLikeClient {
	private FoolsBarrelFabricLikeClient() {
	}

	public static void initClient() {
		FoolsBarrelClient.initClient();
	}
}
