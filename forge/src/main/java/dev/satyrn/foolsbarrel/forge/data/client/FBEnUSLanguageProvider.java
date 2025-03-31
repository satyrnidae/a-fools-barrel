package dev.satyrn.foolsbarrel.forge.data.client;

import dev.satyrn.foolsbarrel.sounds.FoolsBarrelSoundEvents;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class FBEnUSLanguageProvider extends LanguageProvider {
	private final String locale;

	public FBEnUSLanguageProvider(DataGenerator gen, String modid, String locale) {
		super(gen, modid, locale);
		this.locale = locale;
	}

	@Override
	public String getName() {
		return "A Fool's Barrel EN_US Language Provider";
	}

	@Override
	protected void addTranslations() {
		if (!this.locale.equals("en_us")) {
			return;
		}
		this.add("subtitles." + FoolsBarrelSoundEvents.BARREL_EQUIP_SOUND.getId().getPath(), "Barrel equipped");
	}
}
