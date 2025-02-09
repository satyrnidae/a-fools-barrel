package dev.satyrn.foolsbarrel.mixin.entity.ai.goal;

import net.minecraft.block.Blocks;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TrackTargetGoal.class)
public abstract class TrackTargetGoalMixin {

    @Shadow @Final protected MobEntity mob;

	@Inject(method = "canTrack (Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/ai/TargetPredicate;)Z",
			at = @At("HEAD"),
			cancellable = true)
    public void a_fools_barrel$onCanTrack(LivingEntity target,
                           				  TargetPredicate targetPredicate,
										  CallbackInfoReturnable<Boolean> cir) {
        if (target != null && this.mob instanceof HostileEntity) {
            if (this.mob.getEquippedStack(EquipmentSlot.HEAD).isOf(Blocks.BARREL.asItem())) {
                cir.setReturnValue(false);
                cir.cancel();
                return;
            }
            if (target.getEquippedStack(EquipmentSlot.HEAD).isOf(Blocks.BARREL.asItem()) && target.isInSneakingPose()) {
                cir.setReturnValue(false);
                cir.cancel();
            }
        }
    }
}
