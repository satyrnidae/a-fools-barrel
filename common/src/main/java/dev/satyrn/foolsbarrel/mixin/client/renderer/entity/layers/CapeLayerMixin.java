package dev.satyrn.foolsbarrel.mixin.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CapeLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Unique
@SuppressWarnings({"rawtypes", "unchecked"})
@Environment(EnvType.CLIENT)
@Mixin(CapeLayer.class)
public abstract class CapeLayerMixin extends RenderLayer {
	CapeLayerMixin(final RenderLayerParent renderer) {
		super(renderer);
		throw new AssertionError();
	}

	@Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/client/player/AbstractClientPlayer;FFFFFF)V", at = @At("HEAD"), cancellable = true)
	void foolsBarrel$render(final PoseStack stack,
							final MultiBufferSource source,
							final int packedLight,
							final AbstractClientPlayer entity,
							final float limbSwing,
							final float limbSwingAmount,
							final float partialTicks,
							final float ageInTicks,
							final float netHeadYaw,
							final float headPitch,
							final CallbackInfo ci) {
		if (entity.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL)) {
			ci.cancel();
		}
	}
}
