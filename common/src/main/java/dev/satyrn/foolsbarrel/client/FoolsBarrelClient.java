package dev.satyrn.foolsbarrel.client;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.apache.logging.log4j.Level;

@Environment(EnvType.CLIENT)
public final class FoolsBarrelClient {
	private FoolsBarrelClient() {
	}

	public static void initClient() {
		FoolsBarrelCommon.log(Level.INFO, "CLIENT_INIT: Loading A Fool's Barrel client-side");
	}

}
