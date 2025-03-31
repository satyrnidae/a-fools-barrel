package dev.satyrn.foolsbarrel.mixin.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Unique
@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes"})
@Mixin(PlayerModel.class)
public abstract class PlayerModelMixin extends HumanoidModel {
	@Shadow @Final public ModelPart jacket;
	@Shadow @Final public ModelPart leftSleeve;
	@Shadow @Final public ModelPart rightSleeve;
	@Shadow @Final public ModelPart rightPants;
	@Shadow @Final public ModelPart leftPants;
	@Shadow @Final private ModelPart ear;
	@Shadow @Final private ModelPart cloak;

	PlayerModelMixin(ModelPart root) {
		super(root);
	}

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
	void foolsBarrel$setupAnim(final LivingEntity entity,
							   final float limbSwing,
							   final float limbSwingAmount,
							   final float ageInTicks,
							   final float netHeadYaw,
							   final float headPitch,
							   final CallbackInfo ci) {
		// These aren't being set right via the biped model hiding for some reason; setting them manually.
		this.ear.visible = this.head.visible;
		this.cloak.visible = this.body.visible;
		this.jacket.visible = this.body.visible;
		this.leftSleeve.visible = this.leftArm.visible;
		this.rightSleeve.visible = this.rightArm.visible;
		this.leftPants.visible = this.leftLeg.visible;
		this.rightPants.visible = this.rightLeg.visible;
	}
}
