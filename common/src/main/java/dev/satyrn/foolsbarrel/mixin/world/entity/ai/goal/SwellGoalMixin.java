package dev.satyrn.foolsbarrel.mixin.world.entity.ai.goal;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.SwellGoal;
import net.minecraft.world.entity.monster.Creeper;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import javax.annotation.Nullable;

@Mixin(SwellGoal.class)
public abstract class SwellGoalMixin extends Goal {
	@Shadow @Nullable private LivingEntity target;
	@Shadow @Final private Creeper creeper;

	@Inject(method = "canUse()Z", at = @At(value = "INVOKE_ASSIGN", target = "net/minecraft/world/entity/monster/Creeper.getTarget ()Lnet/minecraft/world/entity/LivingEntity;", ordinal = 0), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
	void foolsBarrel$canUse(final CallbackInfoReturnable<Boolean> cir, final @Nullable LivingEntity livingEntity) {
		if (livingEntity != null &&
			livingEntity.isCrouching() &&
			livingEntity.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL)) {
			cir.setReturnValue(false);
			cir.cancel();
		}
	}

	@Inject(method = "tick()V", at = @At("HEAD"), cancellable = true)
	void foolsBarrel$tick(final CallbackInfo ci) {
		if (this.target != null &&
			this.target.isCrouching() &&
			this.target.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL)) {
			this.creeper.setSwellDir(-1);
			ci.cancel();
		}
	}
}
