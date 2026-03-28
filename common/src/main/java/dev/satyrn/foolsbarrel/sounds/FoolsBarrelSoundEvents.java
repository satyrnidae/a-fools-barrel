package dev.satyrn.foolsbarrel.sounds;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public final class FoolsBarrelSoundEvents {
	public static final RegistrySupplier<SoundEvent> BARREL_EQUIP_SOUND;

	static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(FoolsBarrelCommon.MOD_ID,
		Registries.SOUND_EVENT);

	static {
		final ResourceLocation barrelEquipId = FoolsBarrelCommon.createId("block.barrel.equip");
		BARREL_EQUIP_SOUND = SOUND_EVENTS.register(barrelEquipId, () -> SoundEvent.createVariableRangeEvent(barrelEquipId));
	}

	private FoolsBarrelSoundEvents() {
	}

	public static void register() {
		SOUND_EVENTS.register();
	}
}
