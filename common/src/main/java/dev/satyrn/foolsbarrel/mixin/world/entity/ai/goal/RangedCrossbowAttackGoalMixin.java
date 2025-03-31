package dev.satyrn.foolsbarrel.mixin.world.entity.ai.goal;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.RangedCrossbowAttackGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Unique
@Mixin(RangedCrossbowAttackGoal.class)
public abstract class RangedCrossbowAttackGoalMixin extends Goal {
	@Unique private @Nullable Monster foolsBarrel$mob;

	@Inject(method = "<init>(Lnet/minecraft/world/entity/monster/Monster;DF)V", at = @At("RETURN"))
	void foolsBarrel$init(final Monster mob,
						  final double speedModifier,
						  final float attackRadius,
						  final CallbackInfo ci) {
		this.foolsBarrel$mob = mob;
	}

	@Inject(method = "isValidTarget()Z", at = @At("HEAD"), cancellable = true)
	void foolsBarrel$isValidTarget(final CallbackInfoReturnable<Boolean> cir) {
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
