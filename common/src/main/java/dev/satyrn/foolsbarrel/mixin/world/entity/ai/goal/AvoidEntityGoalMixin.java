package dev.satyrn.foolsbarrel.mixin.world.entity.ai.goal;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(AvoidEntityGoal.class)
public abstract class AvoidEntityGoalMixin extends Goal {
	@Shadow @Final protected PathfinderMob mob;
	@Shadow @Nullable protected LivingEntity toAvoid;

	@Inject(method = "canUse()Z", at = @At(value = "INVOKE", target = "net/minecraft/world/entity/ai/util/DefaultRandomPos.getPosAway (Lnet/minecraft/world/entity/PathfinderMob;IILnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/phys/Vec3;", ordinal = 0), cancellable = true)
	void foolsBarrel$canUse(final CallbackInfoReturnable<Boolean> cir) {
		if(this.mob.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL)) {
			cir.setReturnValue(false);
			cir.cancel();
		} else if (this.toAvoid != null && this.toAvoid.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL) && this.toAvoid.isCrouching()) {
			cir.setReturnValue(false);
			cir.cancel();
		}
	}
}
