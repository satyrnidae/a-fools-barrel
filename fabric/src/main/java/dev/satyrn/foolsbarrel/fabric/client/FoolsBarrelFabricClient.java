package dev.satyrn.foolsbarrel.fabric.client;

import dev.satyrn.foolsbarrel.fabriclike.client.FoolsBarrelFabricLikeClient;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public final class FoolsBarrelFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
		FoolsBarrelFabricLikeClient.initClient();
    }
}
