package dev.satyrn.foolsbarrel.forge.data;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.foolsbarrel.forge.data.client.ModUSEnglishLanguageProvider;
import dev.satyrn.foolsbarrel.forge.data.client.ModSoundDefinitionsProvider;
import dev.satyrn.foolsbarrel.forge.data.server.ModItemTagsProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FoolsBarrelCommon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FoolsBarrelGatherDataEvents {
	@SubscribeEvent
	static void onGatherData(final GatherDataEvent event) {
		final var generator = event.getGenerator();
		final var existingFileHelper = event.getExistingFileHelper();

		if (event.includeClient()) {
			generator.addProvider(true, new ModSoundDefinitionsProvider(generator, existingFileHelper));
			generator.addProvider(true, new ModUSEnglishLanguageProvider(generator));
		}
		if (event.includeServer()) {
			generator.addProvider(true, new ModItemTagsProvider(generator, existingFileHelper));
		}
	}
}
