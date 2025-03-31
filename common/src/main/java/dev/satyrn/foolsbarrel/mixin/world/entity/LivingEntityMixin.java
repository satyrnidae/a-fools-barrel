package dev.satyrn.foolsbarrel.mixin.world.entity;

import dev.satyrn.foolsbarrel.api.extensions.Equipment;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {
	LivingEntityMixin(final EntityType<?> entityType, final Level level) {
		super(entityType, level);
		throw new AssertionError();
	}

	@Inject(method = "getEquipmentSlotForItem", at = @At("HEAD"), cancellable = true)
	private static void foolsBarrel$getEquipmentSlotForItem(final ItemStack stack, final CallbackInfoReturnable<EquipmentSlot> cir) {
        if (stack.getItem() instanceof Equipment equipment) {
            cir.setReturnValue(equipment.foolsBarrel$getPreferredSlot());
            cir.cancel();
        } else if (stack.getItem() instanceof BlockItem block && block.getBlock() instanceof Equipment equipment) {
			cir.setReturnValue(equipment.foolsBarrel$getPreferredSlot());
			cir.cancel();
		}
    }
}
