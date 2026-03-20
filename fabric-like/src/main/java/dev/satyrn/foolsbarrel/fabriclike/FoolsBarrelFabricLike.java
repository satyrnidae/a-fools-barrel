package dev.satyrn.foolsbarrel.fabriclike;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;

public final class FoolsBarrelFabricLike {
	private FoolsBarrelFabricLike() {
	}

    public static void init() {
        // Run our common setup.
        FoolsBarrelCommon.init();
		FoolsBarrelCommon.postInit();
    }
}
