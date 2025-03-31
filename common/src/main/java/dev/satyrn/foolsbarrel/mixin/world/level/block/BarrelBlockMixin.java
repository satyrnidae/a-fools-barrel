package dev.satyrn.foolsbarrel.mixin.world.level.block;

import dev.satyrn.foolsbarrel.api.extensions.Equipment;
import dev.satyrn.foolsbarrel.sounds.FoolsBarrelSoundEvents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Wearable;
import net.minecraft.world.level.block.BarrelBlock;
import net.minecraft.world.level.block.BaseEntityBlock;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BarrelBlock.class)
public abstract class BarrelBlockMixin extends BaseEntityBlock implements Wearable, Equipment {
	BarrelBlockMixin(final Properties properties) {
		super(properties);
		throw new AssertionError();
	}

	@Override
	public @Nullable SoundEvent foolsBarrel$getEquipSound() {
		return FoolsBarrelSoundEvents.BARREL_EQUIP_SOUND.get();
	}
}
