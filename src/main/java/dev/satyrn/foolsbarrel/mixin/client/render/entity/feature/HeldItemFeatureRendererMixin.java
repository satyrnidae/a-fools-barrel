package dev.satyrn.foolsbarrel.mixin.client.render.entity.feature;

import net.minecraft.block.Blocks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemFeatureRenderer.class)
public abstract class HeldItemFeatureRendererMixin {

    @Inject(method = "render (Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V",
			at = @At("HEAD"),
			cancellable = true)
    public void a_fools_barrel$onRender(MatrixStack matrixStack,
					   VertexConsumerProvider vertexConsumerProvider,
					   int i,
					   LivingEntity livingEntity,
					   float f,
					   float g,
					   float h,
					   float j,
					   float k,
					   float l,
					   CallbackInfo ci) {
		var equippedStack = livingEntity.getEquippedStack(EquipmentSlot.HEAD);
        if (equippedStack.isOf(Blocks.BARREL.asItem()) && livingEntity.isInSneakingPose()) {
            ci.cancel();
        }
    }
}
