package dev.satyrn.foolsbarrel.quilt.client;

import dev.satyrn.foolsbarrel.fabriclike.client.FoolsBarrelFabricLikeClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
@SuppressWarnings("unused")
public final class FoolsBarrelQuiltClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		FoolsBarrelFabricLikeClient.initClient();
	}
}
