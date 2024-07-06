package dev.satyrn.foolsbarrel.mixin.client.render.entity.feature;

import net.minecraft.block.Blocks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("rawtypes")
@Mixin(ArmorFeatureRenderer.class)
public abstract class ArmorFeatureRendererMixin {
	@Inject(method = "render (Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V",
			at = @At("HEAD"),
			cancellable = true)
    public void a_fools_barrel$onRender(MatrixStack matrices,
                       VertexConsumerProvider vertexConsumers,
                       int light,
                       LivingEntity entity,
                       float f,
                       float g,
                       float h,
                       float j,
                       float k,
                       float l,
                       CallbackInfo ci) {
		var equippedItem = entity.getEquippedStack(EquipmentSlot.HEAD);
		if (equippedItem.isOf(Blocks.BARREL.asItem()) && entity.isInSneakingPose()) {
			ci.cancel();
		}
    }

	@Inject(method = "renderArmor (Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/entity/EquipmentSlot;ILnet/minecraft/client/render/entity/model/BipedEntityModel;)V",
			at = @At(value = "INVOKE",
				 	 target = "net/minecraft/client/render/entity/feature/ArmorFeatureRenderer.setVisible (Lnet/minecraft/client/render/entity/model/BipedEntityModel;Lnet/minecraft/entity/EquipmentSlot;)V",
					 shift = At.Shift.AFTER))
	private void a_fools_barrel$renderArmor$afterSetVisible(MatrixStack matrices,
							 VertexConsumerProvider vertexConsumers,
							 LivingEntity entity,
							 EquipmentSlot armorSlot,
							 int light,
							 BipedEntityModel model,
							 CallbackInfo ci) {
		// Quick and dirty hack to fix the armor arm rendering post armor trims
		var equippedItem = entity.getEquippedStack(EquipmentSlot.HEAD);
		if (equippedItem.isOf(Blocks.BARREL.asItem()) && armorSlot == EquipmentSlot.CHEST) {
			model.leftArm.visible = false;
			model.rightArm.visible = false;
		}
	}
}
