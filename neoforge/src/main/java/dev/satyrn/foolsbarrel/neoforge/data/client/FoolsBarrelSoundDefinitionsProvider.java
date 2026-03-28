package dev.satyrn.foolsbarrel.neoforge.data.client;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.foolsbarrel.sounds.FoolsBarrelSoundEvents;
import dev.satyrn.lepidoptera.neoforge.api.provider.client.sound.ModSoundDefinitionsProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;

public class FoolsBarrelSoundDefinitionsProvider extends ModSoundDefinitionsProvider {
	public FoolsBarrelSoundDefinitionsProvider(PackOutput output, ExistingFileHelper helper) {
		super(FoolsBarrelCommon.class, output, helper);
	}

	@Override
	public void registerSounds() {
		this.add(FoolsBarrelSoundEvents.BARREL_EQUIP_SOUND, SoundDefinition.definition()
			.subtitle("subtitles." + FoolsBarrelSoundEvents.BARREL_EQUIP_SOUND.getId().getPath())
			.with(SoundDefinition.Sound.sound(ResourceLocation.withDefaultNamespace("block/barrel/open1"),
					SoundDefinition.SoundType.SOUND),
				SoundDefinition.Sound.sound(ResourceLocation.withDefaultNamespace("block/barrel/open2"),
					SoundDefinition.SoundType.SOUND)));
	}
}
