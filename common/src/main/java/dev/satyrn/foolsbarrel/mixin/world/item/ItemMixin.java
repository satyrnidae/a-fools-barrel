package dev.satyrn.foolsbarrel.mixin.world.item;

import dev.satyrn.foolsbarrel.api.extensions.Equipment;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemMixin implements ItemLike {
	@Inject(method = "getEquipSound()Lnet/minecraft/sounds/SoundEvent;", at = @At("HEAD"), cancellable = true)
	void foolsBarrel$getEquipSound(final CallbackInfoReturnable<SoundEvent> cir) {
		if (this instanceof Equipment equipment) {
			cir.setReturnValue(equipment.foolsBarrel$getEquipSound());
			cir.cancel();
		}
	}
}
