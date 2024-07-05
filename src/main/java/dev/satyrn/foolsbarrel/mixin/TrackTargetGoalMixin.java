package dev.satyrn.foolsbarrel.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Items;
import net.minecraft.scoreboard.AbstractTeam;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(TrackTargetGoal.class)
public abstract class TrackTargetGoalMixin {

    @Shadow @Final protected MobEntity mob;

	@Inject(method = "canTrack", at = @At("HEAD"), cancellable = true)
    public void onCanTrack(@Nullable LivingEntity target,
                           TargetPredicate targetPredicate,
                           CallbackInfoReturnable<Boolean> cir) {
        if (target != null && this.mob instanceof HostileEntity) {
            if (this.mob.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.BARREL)) {
                cir.setReturnValue(false);
                cir.cancel();
                return;
            }
            if (target.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.BARREL) && target.isInSneakingPose()) {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }

	@Inject(method = "shouldContinue", at = @At(value = "INVOKE", target = "net/minecraft/entity/mob/MobEntity.getTarget ()Lnet/minecraft/entity/LivingEntity;", shift = At.Shift.BY, by = 2), cancellable = true, locals = LocalCapture.CAPTURE_FAILEXCEPTION)
	public void onShouldContinue(CallbackInfoReturnable<Boolean> cir, LivingEntity target) {
		if (target != null) {
			if (target.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.BARREL) && target.isInSneakingPose()) {
				cir.setReturnValue(false);
				cir.cancel();
			}
		}
	}
}
