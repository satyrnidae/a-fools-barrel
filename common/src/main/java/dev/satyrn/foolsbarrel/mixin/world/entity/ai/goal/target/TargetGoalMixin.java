package dev.satyrn.foolsbarrel.mixin.world.entity.ai.goal.target;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.foolsbarrel.data.tags.ModItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import javax.annotation.Nonnull;

@Mixin(TargetGoal.class)
public abstract class TargetGoalMixin extends Goal {
	@Final @Shadow protected Mob mob;

	@Inject(method = "canContinueToUse", at = @At(value = "INVOKE", target = "net/minecraft/world/entity/Mob.getTeam ()Lnet/minecraft/world/scores/Team;"), locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
	void foolsBarrel$canContinueToUse(final @Nonnull CallbackInfoReturnable<Boolean> cir,
									  final @Nonnull LivingEntity livingEntity) {
		if(this.mob.getItemBySlot(EquipmentSlot.HEAD).is(ModItemTags.BARRELS)) {
			cir.setReturnValue(false);
			cir.cancel();
		} else if (FoolsBarrelCommon.getCommonConfig().getShouldHidingRemoveMobAggro()){
			if (livingEntity.getItemBySlot(EquipmentSlot.HEAD).is(ModItemTags.BARRELS) && livingEntity.isCrouching()) {
				cir.setReturnValue(false);
				cir.cancel();
			}
		}
	}
}
