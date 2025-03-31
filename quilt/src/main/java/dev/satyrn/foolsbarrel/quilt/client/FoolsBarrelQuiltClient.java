package dev.satyrn.foolsbarrel.quilt.client;

import dev.satyrn.foolsbarrel.fabriclike.client.FoolsBarrelFabricLikeClient;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;

@ClientOnly
@SuppressWarnings("unused")
public final class FoolsBarrelQuiltClient implements ClientModInitializer {
	@Override
	public void onInitializeClient(ModContainer modContainer) {
		FoolsBarrelFabricLikeClient.initClient();
	}
}
