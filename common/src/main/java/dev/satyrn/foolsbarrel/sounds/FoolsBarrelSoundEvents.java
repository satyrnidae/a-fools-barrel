package dev.satyrn.foolsbarrel.sounds;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public final class FoolsBarrelSoundEvents {
	public static final RegistrySupplier<SoundEvent> BARREL_EQUIP_SOUND;

	static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(FoolsBarrelCommon.MOD_ID,
		Registry.SOUND_EVENT_REGISTRY);

	static {
		final ResourceLocation barrelEquipId = new ResourceLocation(FoolsBarrelCommon.MOD_ID, "block.barrel.equip");
		BARREL_EQUIP_SOUND = SOUND_EVENTS.register(barrelEquipId, () -> new SoundEvent(barrelEquipId));
	}

	private FoolsBarrelSoundEvents() {
	}

	public static void register() {
		SOUND_EVENTS.register();
	}
}
