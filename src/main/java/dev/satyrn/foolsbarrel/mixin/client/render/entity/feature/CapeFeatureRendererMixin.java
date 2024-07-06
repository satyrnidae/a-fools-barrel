package dev.satyrn.foolsbarrel.mixin.client.render.entity.feature;

import net.minecraft.block.Blocks;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CapeFeatureRenderer.class)
public abstract class CapeFeatureRendererMixin {
    @Inject(method = "render (Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/network/AbstractClientPlayerEntity;FFFFFF)V",
			at = @At("HEAD"),
			cancellable = true)
    public void a_fools_barrel$onRender(MatrixStack matrices,
					   VertexConsumerProvider vertexConsumers,
					   int light,
					   AbstractClientPlayerEntity player,
					   float f,
					   float g,
					   float h,
					   float j,
					   float k,
					   float l,
					   CallbackInfo ci) {
		var equippedStack = player.getEquippedStack(EquipmentSlot.HEAD);
        if (equippedStack.isOf(Blocks.BARREL.asItem())) {
            ci.cancel();
        }
    }
}
