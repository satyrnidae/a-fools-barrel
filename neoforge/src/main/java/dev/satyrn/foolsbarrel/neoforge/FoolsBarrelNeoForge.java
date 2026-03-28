package dev.satyrn.foolsbarrel.neoforge;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;

@Mod(FoolsBarrelCommon.MOD_ID)
public final class FoolsBarrelNeoForge {
	public FoolsBarrelNeoForge(final IEventBus modEventBus) {
		// Architectury 13.x handles mod event bus registration automatically.
		// Run our common setup.
		FoolsBarrelCommon.init();
	}
}
