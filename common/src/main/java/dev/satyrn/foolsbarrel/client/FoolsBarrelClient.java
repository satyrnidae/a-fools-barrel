package dev.satyrn.foolsbarrel.client;

import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.lepidoptera.util.NotInitializable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.Level;

@Environment(EnvType.CLIENT)
public final class FoolsBarrelClient {
	private FoolsBarrelClient() {
		NotInitializable.staticClass(FoolsBarrelClient.class);
	}

	public static void initClient() {
		FoolsBarrelCommon.log(Level.INFO, "CLIENT_INIT: Loading A Fool's Barrel client-side");

		ClientPlayerEvent.CLIENT_PLAYER_QUIT.register(player -> {
			FoolsBarrelCommon.clearCommonConfigOverlay();
			FoolsBarrelCommon.clearClientConfigOverlay();
		});
	}

}
