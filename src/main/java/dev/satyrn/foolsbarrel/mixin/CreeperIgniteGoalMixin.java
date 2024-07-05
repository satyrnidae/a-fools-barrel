package foolsbarrel.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.CreeperIgniteGoal;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreeperIgniteGoal.class)
public abstract class CreeperIgniteGoalMixin {

    @Shadow @Final private CreeperEntity creeper;

    @Inject(method = "canStart", at = @At("HEAD"), cancellable = true)
    public void onCanStart(CallbackInfoReturnable<Boolean> cir) {
        if (this.creeper.getTarget() != null) {
            if (this.creeper.getTarget().getEquippedStack(EquipmentSlot.HEAD).isOf(Items.BARREL) &&
                    this.creeper.getTarget().isInSneakingPose()) {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }
}
