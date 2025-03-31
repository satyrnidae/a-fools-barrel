package dev.satyrn.foolsbarrel.mixin.world.entity.ai.goal;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RangedAttackGoal;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import javax.annotation.Nullable;

@Mixin(RangedAttackGoal.class)
public abstract class RangedAttackGoalMixin extends Goal {
	@Shadow @Final private Mob mob;

	@Inject(method = "canUse()Z", at = @At(value = "INVOKE_ASSIGN", target = "net/minecraft/world/entity/Mob.getTarget ()Lnet/minecraft/world/entity/LivingEntity;", ordinal = 0), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
	void foolsBarrel$canUse(final CallbackInfoReturnable<Boolean> cir, final @Nullable LivingEntity livingEntity) {
		if (this.mob.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL)) {
			cir.setReturnValue(false);
			cir.cancel();
		} else if (livingEntity != null && livingEntity.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL) && livingEntity.isCrouching()) {
			cir.setReturnValue(false);
			cir.cancel();
		}
	}

	@Inject(method = "canContinueToUse()Z", at = @At("HEAD"), cancellable = true)
	void foolsBarrel$canContinueToUse(final CallbackInfoReturnable<Boolean> cir) {
		if (this.mob.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL)) {
			cir.setReturnValue(false);
			cir.cancel();
		} else {
			final @Nullable LivingEntity livingEntity = this.mob.getTarget();
			if (livingEntity != null && livingEntity.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL) && livingEntity.isCrouching()) {
				cir.setReturnValue(false);
				cir.cancel();
			}
		}
	}
}
