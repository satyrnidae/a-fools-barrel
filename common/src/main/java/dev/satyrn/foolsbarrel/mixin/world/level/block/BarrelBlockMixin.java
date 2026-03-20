package dev.satyrn.foolsbarrel.mixin.world.level.block;

import dev.satyrn.foolsbarrel.sounds.FoolsBarrelSoundEvents;
import dev.satyrn.lepidoptera.api.world.item.Equipment;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Wearable;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.BaseEntityBlock;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BarrelBlock.class)
@Implements({
	@Interface(iface = Equipment.class, prefix = "lapix$")
})
public abstract class BarrelBlockMixin extends BaseEntityBlock implements Wearable {
	BarrelBlockMixin(final Properties properties) {
		super(properties);
		throw new AssertionError();
	}

	public @Nullable SoundEvent lapix$getEquipSound() {
		return FoolsBarrelSoundEvents.BARREL_EQUIP_SOUND.get();
	}
}
