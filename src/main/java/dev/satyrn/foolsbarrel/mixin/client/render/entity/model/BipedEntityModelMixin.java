package dev.satyrn.foolsbarrel.mixin.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin {
	@Shadow @Final public ModelPart leftArm;
	@Shadow @Final public ModelPart rightArm;
	@Shadow @Final public ModelPart head;
	@Shadow @Final public ModelPart body;
	@Shadow @Final public ModelPart leftLeg;
	@Shadow @Final public ModelPart rightLeg;

	// Inject this after the isInSwimmingPose call and istore
    @Inject(method = "setAngles (Lnet/minecraft/entity/LivingEntity;FFFFF)V",
			at = @At(value = "INVOKE",
					 target = "net/minecraft/entity/LivingEntity.isInSwimmingPose ()Z",
					 ordinal = 0,
				     shift = At.Shift.BY,
					 by = 2))
    public void a_fools_barrel$setAngles$afterIsInSwimmingPose(LivingEntity entity,
															   float f,
															   float g,
															   float h,
															   float i,
															   float j,
															   CallbackInfo ci) {
		if (!(entity instanceof EndermanEntity || entity instanceof ArmorStandEntity)) {
			boolean hasBarrelEquipped = entity.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.BARREL.asItem());

			this.leftArm.visible = !hasBarrelEquipped;
			this.rightArm.visible = !hasBarrelEquipped;
			this.head.visible = !hasBarrelEquipped;
			if (entity instanceof PlayerEntity player) {
				if (player.isInSneakingPose()) {
					this.body.visible = this.leftLeg.visible = this.rightLeg.visible = !hasBarrelEquipped;
				}
			}
		}
    }
}
