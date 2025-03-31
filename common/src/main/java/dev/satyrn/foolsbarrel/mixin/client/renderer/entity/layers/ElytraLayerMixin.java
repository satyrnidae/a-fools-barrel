package dev.satyrn.foolsbarrel.mixin.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
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
@SuppressWarnings({"rawtypes", "unchecked"})
@Mixin(ElytraLayer.class)
public abstract class ElytraLayerMixin extends RenderLayer {
	ElytraLayerMixin(final RenderLayerParent renderer) {
		super(renderer);
		throw new AssertionError();
	}

	@Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V", at = @At("HEAD"), cancellable = true)
	void foolsBarrel$render(final PoseStack stack,
							final MultiBufferSource buffer,
							final int packedLight,
							final LivingEntity entity,
							final float limbSwing,
							final float limbSwingAmount,
							final float partialTicks,
							final float ageInTicks,
							final float netHeadYaw,
							final float headPitch,
							final CallbackInfo ci) {
		if (entity.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL) && entity.isCrouching()) {
			ci.cancel();
		}
	}
}
