package dev.satyrn.foolsbarrel.neoforge.events;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@EventBusSubscriber(modid = FoolsBarrelCommon.MOD_ID)
public final class FoolsBarrelLifecycleEvents {
	private FoolsBarrelLifecycleEvents() {
	}

	@SubscribeEvent
	static void onCommonSetup(final FMLCommonSetupEvent event) {
		FoolsBarrelCommon.postInit();
	}
}
