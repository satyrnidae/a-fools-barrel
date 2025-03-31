package dev.satyrn.foolsbarrel.mixin.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.AgeableListModel;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.player.Player;
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
@Mixin(HumanoidModel.class)
public abstract class HumanoidModelMixin extends AgeableListModel implements ArmedModel, HeadedModel {
	@Final @Shadow public ModelPart hat;
	@Final @Shadow public ModelPart rightArm;
	@Final @Shadow public ModelPart leftArm;
	@Final @Shadow public ModelPart head;
	@Final @Shadow public ModelPart body;
	@Final @Shadow public ModelPart leftLeg;
	@Final @Shadow public ModelPart rightLeg;

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/LivingEntity;FFFFF)V", at = @At("HEAD"))
	void foolsBarrel$setupAnim(final LivingEntity entity,
							   final float limbSwing,
							   final float limbSwingAmount,
							   final float ageInTicks,
							   final float netHeadYaw,
							   final float headPitch,
							   final CallbackInfo ci) {
		if (!(entity instanceof EnderMan) && !(entity instanceof ArmorStand)) {
			boolean isBarrelEquipped = entity.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL);
			this.leftArm.visible = this.rightArm.visible = this.head.visible = this.hat.visible = !isBarrelEquipped;
			if (entity instanceof final Player player && player.isCrouching()) {
				this.body.visible = this.rightLeg.visible = this.leftLeg.visible = !isBarrelEquipped;
			}
		}
	}
}
