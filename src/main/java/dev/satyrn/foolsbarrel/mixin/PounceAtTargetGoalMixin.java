package dev.satyrn.foolsbarrel.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.PounceAtTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PounceAtTargetGoal.class)
public abstract class PounceAtTargetGoalMixin {

    @Shadow @Final private MobEntity mob;

    @Inject(method = "canStart", at = @At("HEAD"), cancellable = true)
    public void onCanStart(CallbackInfoReturnable<Boolean> cir) {
        if (this.mob.getTarget() != null) {
            if (this.mob.getTarget().getEquippedStack(EquipmentSlot.HEAD).isOf(Items.BARREL) &&
                    this.mob.getTarget().isInSneakingPose()) {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }
}
