package dev.satyrn.foolsbarrel.forge.client;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.foolsbarrel.client.FoolsBarrelClient;
import dev.satyrn.foolsbarrel.forge.client.gui.overlay.BarrelOverlay;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = FoolsBarrelCommon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class FoolsBarrelClientEvents {
	private FoolsBarrelClientEvents() {
	}

	@SubscribeEvent
	static void onClientSetup(final FMLClientSetupEvent event) {
		// Client init
		FoolsBarrelClient.initClient();
	}

	@SubscribeEvent
	static void onRegisterGuiOverlays(final RegisterGuiOverlaysEvent event) {
		event.registerBelow(VanillaGuiOverlay.HOTBAR.id(), "barrel", new BarrelOverlay());
	}
}
