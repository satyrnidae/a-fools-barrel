package foolsbarrel.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TargetGoal.class)
public abstract class TargetGoalMixin extends TrackTargetGoalMixin {

    @Inject(method = "canStart", at = @At("HEAD"), cancellable = true)
    public void onCanStart(CallbackInfoReturnable<Boolean> cir) {
        if (this.mob.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.BARREL)) {
            cir.setReturnValue(false);
            cir.cancel();
        } else if (this.mob.getTarget() != null &&
                this.mob.getTarget().getEquippedStack(EquipmentSlot.HEAD).isOf(Items.BARREL) &&
                this.mob.getTarget().isInSneakingPose()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
