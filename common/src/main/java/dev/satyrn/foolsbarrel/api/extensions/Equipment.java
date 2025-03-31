package dev.satyrn.foolsbarrel.api.extensions;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EquipmentSlot;

import javax.annotation.Nullable;

public interface Equipment {
	default EquipmentSlot foolsBarrel$getPreferredSlot() {
		return EquipmentSlot.HEAD;
	}

	default @Nullable SoundEvent foolsBarrel$getEquipSound() {
		return null;
	}
}
