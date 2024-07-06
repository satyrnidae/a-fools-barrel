package dev.satyrn.foolsbarrel.mixin.entity.ai.goal;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.goal.BowAttackGoal;
import net.minecraft.entity.mob.HostileEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BowAttackGoal.class)
public abstract class BowAttackGoalMixin {
	@Shadow @Final private HostileEntity actor;

	@Inject(method = "canStart ()Z",
			at = @At("HEAD"),
			cancellable = true)
    public void a_fools_barrel$onCanStart(CallbackInfoReturnable<Boolean> cir) {
        if (this.actor.getEquippedStack(EquipmentSlot.HEAD).isOf(Blocks.BARREL.asItem())) {
            cir.setReturnValue(false);
            cir.cancel();
        } else if (this.actor.getTarget() != null &&
			this.actor.getTarget().getEquippedStack(EquipmentSlot.HEAD).isOf(Blocks.BARREL.asItem()) &&
			this.actor.getTarget().isInSneakingPose()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
