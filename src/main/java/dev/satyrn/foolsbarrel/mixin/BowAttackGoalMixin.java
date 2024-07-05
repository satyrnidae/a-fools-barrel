package dev.satyrn.foolsbarrel.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ai.RangedAttackMob;
import net.minecraft.entity.ai.goal.BowAttackGoal;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BowAttackGoal.class)
public abstract class BowAttackGoalMixin<T extends HostileEntity & RangedAttackMob> {

    private HostileEntity mob;

    @Inject(method = "<init>", at = @At("RETURN"))
    public void onInit(T actor, double speed, int attackInterval, float range, CallbackInfo ci) {
        this.mob = actor;
    }

    @Inject(method = "canStart", at = @At("HEAD"), cancellable = true)
    public void onCanStart(CallbackInfoReturnable<Boolean> cir) {
        if (mob.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.BARREL)) {
            cir.setReturnValue(false);
            cir.cancel();
        } else if (mob.getTarget() != null &&
                mob.getTarget().getEquippedStack(EquipmentSlot.HEAD).isOf(Items.BARREL) &&
                mob.getTarget().isInSneakingPose()) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
