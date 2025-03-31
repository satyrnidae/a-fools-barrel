package dev.satyrn.foolsbarrel.forge.data.client;

import dev.satyrn.foolsbarrel.sounds.FoolsBarrelSoundEvents;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.SoundDefinition;
import net.minecraftforge.common.data.SoundDefinitionsProvider;

public class FBSoundDefinitionsProvider extends SoundDefinitionsProvider {
	public FBSoundDefinitionsProvider(DataGenerator generator, String modId, ExistingFileHelper helper) {
		super(generator, modId, helper);
	}

	@Override
	public void registerSounds() {
		this.add(FoolsBarrelSoundEvents.BARREL_EQUIP_SOUND, SoundDefinition.definition()
			.subtitle("subtitles." + FoolsBarrelSoundEvents.BARREL_EQUIP_SOUND.getId().getPath())
			.with(SoundDefinition.Sound.sound(new ResourceLocation("block/barrel/open1"),
					SoundDefinition.SoundType.SOUND),
				SoundDefinition.Sound.sound(new ResourceLocation("block/barrel/open2"),
					SoundDefinition.SoundType.SOUND)));
	}

	@Override
	public String getName() {
		return "A Fool's Barrel Sound Events";
	}
}
