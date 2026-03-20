package dev.satyrn.foolsbarrel.forge.client;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.foolsbarrel.client.FoolsBarrelClient;
import dev.satyrn.foolsbarrel.config.ClientSideConfig;
import dev.satyrn.foolsbarrel.forge.client.gui.overlay.BarrelOverlay;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = FoolsBarrelCommon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class FoolsBarrelClientEvents {
	private FoolsBarrelClientEvents() {
	}

	@SubscribeEvent
	@SuppressWarnings("removal")
	static void onClientSetup(final FMLClientSetupEvent event) {
		// Client init
		FoolsBarrelClient.initClient();
		ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class,
			() -> new ConfigScreenHandler.ConfigScreenFactory(
				(client, parent) -> AutoConfig.getConfigScreen(ClientSideConfig.class, parent).get()));
	}

	@SubscribeEvent
	static void onRegisterGuiOverlays(final RegisterGuiOverlaysEvent event) {
		event.registerBelow(VanillaGuiOverlay.HOTBAR.id(), "barrel", new BarrelOverlay());
	}
}
