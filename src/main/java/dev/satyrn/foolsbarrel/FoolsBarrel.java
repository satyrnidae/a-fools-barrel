package dev.satyrn.foolsbarrel;

import net.fabricmc.api.ModInitializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.Holder;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FoolsBarrel implements ModInitializer {
	public static final String MOD_ID = "foolsbarrel";

    public static final Identifier BARREL_EYE_HOLES = Identifier.of(MOD_ID, "textures/misc/barrel_eye_holes.png");
	public static final Identifier BARREL_EQUIP_SOUND_ID = Identifier.of(MOD_ID, "block.barrel.equip");

	public static Holder<SoundEvent> BARREL_EQUIP_SOUND;
    public static final Logger log = LogManager.getLogger();

    @Override
    public void onInitialize() {
        log.info("Loading A Fool's Barrel...");

		BARREL_EQUIP_SOUND = Registry.registerHolder(Registries.SOUND_EVENT, BARREL_EQUIP_SOUND_ID, SoundEvent.createVariableRangeEvent(BARREL_EQUIP_SOUND_ID));
    }
}
