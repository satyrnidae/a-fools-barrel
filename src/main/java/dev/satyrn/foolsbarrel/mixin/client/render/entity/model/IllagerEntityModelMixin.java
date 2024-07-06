package dev.satyrn.foolsbarrel.mixin.client.render.entity.model;

import net.minecraft.block.Blocks;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.IllagerEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.IllagerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IllagerEntityModel.class)
public abstract class IllagerEntityModelMixin {
    @Shadow @Final private ModelPart head;
    @Shadow @Final private ModelPart hat;
    @Shadow @Final private ModelPart arms;
    @Shadow @Final private ModelPart leftArm;
    @Shadow @Final private ModelPart rightArm;

    @Inject(method = "setAngles (Lnet/minecraft/entity/mob/IllagerEntity;FFFFF)V",
			at = @At("HEAD"))
    public void a_fools_barrel$onSetAngles(IllagerEntity illagerEntity,
										   float f,
										   float g,
										   float h,
										   float i,
										   float j,
										   CallbackInfo ci) {
		var equippedStack = illagerEntity.getEquippedStack(EquipmentSlot.HEAD);
        boolean wearingBarrel = equippedStack.isOf(Blocks.BARREL.asItem());

        this.head.visible = !wearingBarrel;

        if (wearingBarrel) {
            this.hat.pivotY = 1.5f;
        } else {
            this.hat.pivotY = 0.0f;
        }
    }

    @Inject(method = "setAngles (Lnet/minecraft/entity/mob/IllagerEntity;FFFFF)V",
			at = @At("TAIL"))
    public void a_fools_barrel$afterSetAngles(IllagerEntity illagerEntity,
											  float f,
											  float g,
											  float h,
											  float i,
											  float j,
											  CallbackInfo ci) {
		var equippedStack = illagerEntity.getEquippedStack(EquipmentSlot.HEAD);
        if (equippedStack.isOf(Blocks.BARREL.asItem())) {
            this.head.pitch = 0.0f;
            this.head.yaw = 0.0f;

            IllagerEntity.State state = illagerEntity.getState();

            if (state.equals(IllagerEntity.State.CROSSED)) {
                this.arms.visible = false;
            } else {
                this.leftArm.visible = false;
                this.rightArm.visible = false;
            }
        }
    }
}
