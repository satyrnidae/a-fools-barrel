package dev.satyrn.foolsbarrel.mixin.block;

import dev.satyrn.foolsbarrel.FoolsBarrel;
import net.minecraft.block.BarrelBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Equippable;
import net.minecraft.registry.Holder;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

/**
 * Allows the barrel block to be equipped in player and mobs' HEAD armor slot.
 */
@Mixin(BarrelBlock.class)
public abstract class BarrelBlockMixin implements Equippable {
	/**
	 * Gets the preferred slot for this item.
	 * @return {@link EquipmentSlot}{@code .HEAD}
	 */
    @Unique
    @Override
    public EquipmentSlot getPreferredSlot() {
        return EquipmentSlot.HEAD;
    }

	/**
	 * Gets the equip sound for the block.
	 * @return The barrel equip sound.
	 */
	@Unique
	@Override
	public Holder<SoundEvent> getEquipSound() {
		 return FoolsBarrel.BARREL_EQUIP_SOUND;
	}
}
