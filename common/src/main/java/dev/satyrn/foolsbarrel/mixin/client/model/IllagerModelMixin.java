package dev.satyrn.foolsbarrel.mixin.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.AbstractIllager;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Unique
@Environment(EnvType.CLIENT)
@SuppressWarnings("rawtypes")
@Mixin(IllagerModel.class)
public abstract class IllagerModelMixin extends HierarchicalModel implements ArmedModel, HeadedModel {
	@Shadow @Final private ModelPart head;
	@Shadow @Final private ModelPart hat;
	@Shadow @Final private ModelPart arms;
	@Shadow @Final private ModelPart leftArm;
	@Shadow @Final private ModelPart rightArm;

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/monster/AbstractIllager;FFFFF)V", at = @At("HEAD"))
	void foolsBarrel$setupAnim$head(final AbstractIllager entity,
									final float limbSwing,
									final float limbSwingAmount,
									final float ageInTicks,
									final float netHeadYaw,
									final float headPitch,
									final CallbackInfo ci) {
		boolean wearingBarrel = entity.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL);
		this.head.visible = !wearingBarrel;

		if (wearingBarrel) {
			this.hat.y = 1.5f;
		} else {
			this.hat.y = 0.0f;
		}
	}

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/monster/AbstractIllager;FFFFF)V", at = @At("TAIL"))
	void foolsBarrel$setupAnim$tail(final AbstractIllager entity,
									final float limbSwing,
									final float limbSwingAmount,
									final float ageInTicks,
									final float netHeadYaw,
									final float headPitch,
									final CallbackInfo ci) {
		boolean wearingBarrel = entity.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL);
		if (wearingBarrel) {
			this.head.xRot = 0.0f;
			this.head.yRot = 0.0f;

			AbstractIllager.IllagerArmPose state = entity.getArmPose();

			if (state.equals(AbstractIllager.IllagerArmPose.CROSSED)) {
				this.arms.visible = false;
			} else {
				this.leftArm.visible = false;
				this.rightArm.visible = false;
			}
		}
	}
}
