package dev.satyrn.foolsbarrel.mixin.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Unique
@Environment(EnvType.CLIENT)
@SuppressWarnings({"unchecked", "rawtypes"})
@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin extends RenderLayer {
	HumanoidArmorLayerMixin(final RenderLayerParent renderer) {
		super(renderer);
		throw new AssertionError();
	}

	@Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V", at = @At("HEAD"), cancellable = true)
	void foolsBarrel$render(final PoseStack poseStack,
							final MultiBufferSource bufferSource,
							final int light,
							final LivingEntity livingEntity,
							final float limbAngle,
							final float limbDistance,
							final float tickDelta,
							final float animationProgress,
							final float headYaw,
							final float headPitch,
							final CallbackInfo ci) {
		if (livingEntity.isCrouching() && livingEntity.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL)) {
			ci.cancel();
		}
	}
}
