package dev.satyrn.foolsbarrel.mixin;

import dev.satyrn.foolsbarrel.FoolsBarrel;
import net.minecraft.block.BarrelBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Equippable;
import net.minecraft.registry.Holder;
import net.minecraft.sound.SoundEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(BarrelBlock.class)
public abstract class BarrelBlockMixin implements Equippable {
    @Unique
    @Override
    public EquipmentSlot getPreferredSlot() {
        return EquipmentSlot.HEAD;
    }

	@Override
	public Holder<SoundEvent> getEquipSound() {
		 return FoolsBarrel.BARREL_EQUIP_SOUND;
	}
}
