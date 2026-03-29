package dev.satyrn.foolsbarrel.neoforge.data;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.foolsbarrel.neoforge.data.client.ModUSEnglishLanguageProvider;
import dev.satyrn.foolsbarrel.neoforge.data.client.FoolsBarrelSoundDefinitionsProvider;
import dev.satyrn.foolsbarrel.neoforge.data.server.ModItemTagsProvider;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.data.event.GatherDataEvent;

@EventBusSubscriber(modid = FoolsBarrelCommon.MOD_ID)
public class FoolsBarrelGatherDataEvents {
	@SubscribeEvent
	static void onGatherData(final GatherDataEvent event) {
		final var generator = event.getGenerator();
		final var packOutput = generator.getPackOutput();
		final var lookupProvider = event.getLookupProvider();
		final var existingFileHelper = event.getExistingFileHelper();

		if (event.includeClient()) {
			generator.addProvider(true, new FoolsBarrelSoundDefinitionsProvider(packOutput, existingFileHelper));
			generator.addProvider(true, new ModUSEnglishLanguageProvider(packOutput));
		}
		if (event.includeServer()) {
			generator.addProvider(true, new ModItemTagsProvider(packOutput, lookupProvider, existingFileHelper));
		}
	}
}
