package dev.satyrn.foolsbarrel.forge.data;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.foolsbarrel.forge.data.client.FBEnUSLanguageProvider;
import dev.satyrn.foolsbarrel.forge.data.client.FBSoundDefinitionsProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FoolsBarrelCommon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FoolsBarrelGatherDataEvents {
	@SubscribeEvent
	static void onGatherData(final GatherDataEvent event) {
		event.getGenerator().addProvider(
			event.includeClient(),
			new FBSoundDefinitionsProvider(event.getGenerator(), event.getModContainer().getModId(), event.getExistingFileHelper())
		);
		event.getGenerator().addProvider(
			event.includeClient(),
			new FBEnUSLanguageProvider(event.getGenerator(), event.getModContainer().getModId(), "en_us")
		);
	}
}
