package dev.satyrn.foolsbarrel.mixin.entity.ai.goal;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.CreeperIgniteGoal;
import net.minecraft.entity.mob.CreeperEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CreeperIgniteGoal.class)
public abstract class CreeperIgniteGoalMixin {
    @Shadow @Final private CreeperEntity creeper;

    @Inject(method = "canStart ()Z",
			at = @At("HEAD"),
			cancellable = true)
    public void a_fools_barrel$onCanStart(CallbackInfoReturnable<Boolean> cir) {
        if (this.creeper.getTarget() != null) {
            if (this.creeper.getTarget().getEquippedStack(EquipmentSlot.HEAD).isOf(Blocks.BARREL.asItem()) &&
                    this.creeper.getTarget().isInSneakingPose()) {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }
}
