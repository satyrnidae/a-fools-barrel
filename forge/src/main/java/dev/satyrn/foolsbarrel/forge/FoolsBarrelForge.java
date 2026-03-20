package dev.satyrn.foolsbarrel.forge;

import dev.architectury.platform.forge.EventBuses;
import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(FoolsBarrelCommon.MOD_ID)
public final class FoolsBarrelForge {
	public FoolsBarrelForge(final FMLJavaModLoadingContext modLoadingContext) {
		// Submit our event bus to let Architectury API register our content on the right time.
		EventBuses.registerModEventBus(FoolsBarrelCommon.MOD_ID, modLoadingContext.getModEventBus());

		// Run our common setup.
		FoolsBarrelCommon.init();
	}
}
