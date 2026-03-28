package dev.satyrn.foolsbarrel.neoforge.client;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.foolsbarrel.client.FoolsBarrelClient;
import dev.satyrn.foolsbarrel.config.ClientSideConfig;
import dev.satyrn.foolsbarrel.neoforge.client.gui.overlay.BarrelOverlay;
import dev.satyrn.lepidoptera.config.LepidopteraConfig;
import me.shedaniel.autoconfig.AutoConfig;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Contract;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(value = Dist.CLIENT, modid = FoolsBarrelCommon.MOD_ID)
public final class FoolsBarrelClientEvents {
	@Contract(pure = true)
	private FoolsBarrelClientEvents() {
	}

	@SubscribeEvent
	static void onClientSetup(final FMLClientSetupEvent event) {
		// Client init
		FoolsBarrelClient.initClient();

		ModLoadingContext.get()
			.registerExtensionPoint(IConfigScreenFactory.class,
				() -> (mc, screen) -> AutoConfig.getConfigScreen(ClientSideConfig.class, screen).get());

	}

	@SubscribeEvent
	static void onRegisterGuiLayers(final RegisterGuiLayersEvent event) {
		event.registerAbove(VanillaGuiLayers.HOTBAR, ResourceLocation.fromNamespaceAndPath(FoolsBarrelCommon.MOD_ID, "barrel"), new BarrelOverlay());
	}
}
