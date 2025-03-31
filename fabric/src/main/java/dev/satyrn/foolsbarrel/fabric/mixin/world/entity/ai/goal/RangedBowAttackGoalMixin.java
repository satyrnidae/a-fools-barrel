package dev.satyrn.foolsbarrel.fabric.mixin.world.entity.ai.goal;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RangedBowAttackGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(RangedBowAttackGoal.class)
public abstract class RangedBowAttackGoalMixin extends Goal {
	@Unique private @Nullable Mob foolsBarrel$mob;

	@Inject(method = "<init>(Lnet/minecraft/world/entity/monster/Monster;DIF)V", at = @At("RETURN"))
	void foolsBarrel$init(final Monster mob,
						  final double speedModifier,
						  final int attackIntervalMin,
						  final float attackRadius,
						  final CallbackInfo ci) {
		this.foolsBarrel$mob = mob;
	}

	@Inject(method = "canUse()Z", at = @At("HEAD"), cancellable = true)
	void foolsBarrel$canUse(final CallbackInfoReturnable<Boolean> cir) {
		if (this.foolsBarrel$mob == null) {
			throw new AssertionError();
		}
		if (this.foolsBarrel$mob.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL)) {
			cir.setReturnValue(false);
			cir.cancel();
		} else {
			final @Nullable LivingEntity target = this.foolsBarrel$mob.getTarget();
			if (target != null && target.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL) && target.isCrouching()) {
				cir.setReturnValue(false);
				cir.cancel();
			}
		}
	}

	@Inject(method = "canContinueToUse()Z", at = @At("HEAD"), cancellable = true)
	void foolsBarrel$canContinueToUse(final CallbackInfoReturnable<Boolean> cir) {
		if (this.foolsBarrel$mob == null) {
			throw new AssertionError();
		}
		if (this.foolsBarrel$mob.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL)) {
			cir.setReturnValue(false);
			cir.cancel();
		} else {
			final @Nullable LivingEntity target = this.foolsBarrel$mob.getTarget();
			if (target != null && target.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL) && target.isCrouching()) {
				cir.setReturnValue(false);
				cir.cancel();
			}
		}
	}
}
