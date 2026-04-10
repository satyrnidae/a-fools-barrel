package dev.satyrn.foolsbarrel.fabriclike;

import dev.architectury.event.events.common.LifecycleEvent;
import dev.satyrn.foolsbarrel.FoolsBarrelCommon;

public final class FoolsBarrelFabricLike {
	private FoolsBarrelFabricLike() {
	}

    public static void init() {
        // Run our common setup.
        FoolsBarrelCommon.init();
		FoolsBarrelCommon.postInit();

		LifecycleEvent.SERVER_STARTED.register(FoolsBarrelCommon::serverStarted);
		LifecycleEvent.SERVER_STOPPED.register(server -> FoolsBarrelCommon.serverStopped());
    }
}
