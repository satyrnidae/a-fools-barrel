package dev.satyrn.foolsbarrel.mixin.world.item;

import dev.satyrn.foolsbarrel.api.extensions.Equipment;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;

@Mixin(BlockItem.class)
public abstract class BlockItemMixin extends Item {

	@Shadow
	public abstract Block getBlock();

	BlockItemMixin(Properties properties) {
		super(properties);
		throw new AssertionError();
	}

	@Unique
	@Override
	public @Nullable SoundEvent getEquipSound() {
		if (this.getBlock() instanceof Equipment equipmentBlock) {
			return equipmentBlock.foolsBarrel$getEquipSound();
		}
		return super.getEquipSound();
	}
}
