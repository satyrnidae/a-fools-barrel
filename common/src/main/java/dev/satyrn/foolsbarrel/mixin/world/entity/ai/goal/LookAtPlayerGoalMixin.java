package dev.satyrn.foolsbarrel.mixin.world.entity.ai.goal;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(LookAtPlayerGoal.class)
public abstract class LookAtPlayerGoalMixin extends Goal {
	@Shadow @Final protected Mob mob;
	@Shadow @Nullable protected Entity lookAt;

	@Inject(method = "canUse()Z", at = @At(value = "RETURN", ordinal = 1), cancellable = true)
	void foolsBarrel$canUse(final CallbackInfoReturnable<Boolean> cir) {
		if (this.mob.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL)) {
			cir.setReturnValue(false);
			cir.cancel();
		} else if (this.lookAt instanceof LivingEntity livingEntity) {
			if (livingEntity.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL) && livingEntity.isCrouching()) {
				cir.setReturnValue(false);
				cir.cancel();
			}
		}
	}

	@Inject(method = "canContinueToUse()Z", at = @At(value = "RETURN", ordinal = 2), cancellable = true)
	void foolsBarrel$canContinueToUse(final CallbackInfoReturnable<Boolean> cir) {
		if (this.mob.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL)) {
			cir.setReturnValue(false);
			cir.cancel();
		} else if (this.lookAt instanceof LivingEntity livingEntity) {
			if (livingEntity.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL) && livingEntity.isCrouching()) {
				cir.setReturnValue(false);
				cir.cancel();
			}
		}
	}
}
