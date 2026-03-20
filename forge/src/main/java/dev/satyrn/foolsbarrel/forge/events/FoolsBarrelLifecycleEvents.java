package dev.satyrn.foolsbarrel.forge.events;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = FoolsBarrelCommon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class FoolsBarrelLifecycleEvents {
	private FoolsBarrelLifecycleEvents() {
	}

	@SubscribeEvent
	static void onCommonSetup(final FMLCommonSetupEvent event) {
		FoolsBarrelCommon.postInit();
	}
}
