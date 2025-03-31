package dev.satyrn.foolsbarrel.mixin.world.entity.ai.goal;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.LeapAtTargetGoal;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(LeapAtTargetGoal.class)
public abstract class LeapAtTargetGoalMixin extends Goal {

	@Shadow private @Nullable LivingEntity target;

	@Shadow @Final private Mob mob;

	@Inject(method = "canUse()Z", at = @At(value = "INVOKE_ASSIGN", target = "net/minecraft/world/entity/Mob.getTarget ()Lnet/minecraft/world/entity/LivingEntity;"), cancellable = true)
	void foolsBarrel$canUse(final CallbackInfoReturnable<Boolean> cir) {
		if (this.target != null && this.target.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL) && this.target.isCrouching()) {
			cir.setReturnValue(false);
			cir.cancel();
		}
	}

	@Inject(method = "canContinueToUse()Z", at = @At("HEAD"), cancellable = true)
	void foolsBarrel$canContinueToUse(final CallbackInfoReturnable<Boolean> cir) {
		if (this.target != null && this.target.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL) && this.target.isCrouching()) {
			cir.setReturnValue(false);
			cir.cancel();
		}
	}
}
