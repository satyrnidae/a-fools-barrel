package dev.satyrn.foolsbarrel.mixin.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.satyrn.foolsbarrel.api.extensions.client.renderer.entity.layers.BipedLayerExtensions;
import dev.satyrn.foolsbarrel.data.tags.ModItemTags;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Implements;
import org.spongepowered.asm.mixin.Interface;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Unique
@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
@Mixin(CustomHeadLayer.class)
@Implements({
	@Interface(iface = BipedLayerExtensions.class, prefix = "bipedLayerExt$")
})
public abstract class CustomHeadLayerMixin extends RenderLayer {
	@Unique public boolean foolsBarrel$isBiped;

	CustomHeadLayerMixin(final RenderLayerParent renderer) {
		super(renderer);
		throw new AssertionError();
	}

	@Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V", at = @At("HEAD"), cancellable = true)
	void foolsBarrel$render(final PoseStack poseStack,
							final MultiBufferSource buffer,
							final int packedLight,
							final LivingEntity livingEntity,
							final float limbSwing,
							final float limbSwingAmount,
							final float partialTicks,
							final float ageInTicks,
							final float netHeadYaw,
							final float headPitch,
							final CallbackInfo ci) {
		final ItemStack itemStack = livingEntity.getItemBySlot(EquipmentSlot.HEAD);

		if (itemStack.isEmpty() || this.foolsBarrel$isBiped && itemStack.is(ModItemTags.BARRELS)) {
			// We are rendering a biped with a barrel on their head
			ci.cancel();
		}
	}

	@Unique
	public void bipedLayerExt$setBiped(boolean value) {
		this.foolsBarrel$isBiped = value;
	}
}
