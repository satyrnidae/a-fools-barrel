package dev.satyrn.foolsbarrel.mixin.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.satyrn.foolsbarrel.api.extensions.client.renderer.entity.layers.BipedLayerExtensions;
import dev.satyrn.foolsbarrel.client.BarrelLayer;
import dev.satyrn.foolsbarrel.mixin.client.accessors.renderer.entity.LivingEntityRendererAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Unique
@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
@Mixin(PlayerRenderer.class)
public abstract class PlayerRendererMixin extends LivingEntityRenderer {
	PlayerRendererMixin(final EntityRendererProvider.Context context,
						final EntityModel model,
						final float shadowRadius) {
		super(context, model, shadowRadius);
		throw new AssertionError();
	}

	@Inject(method = "<init>(Lnet/minecraft/client/renderer/entity/EntityRendererProvider$Context;Z)V", at = @At("RETURN"))
	void foolsBarrel$init(final EntityRendererProvider.Context context,
						  final boolean useSlimModel,
						  final CallbackInfo ci) {
		// Add the barrel feature
		this.addLayer(new BarrelLayer(this));

		// Find the extant head feature renderer and set the biped flag to true
		Optional<RenderLayer> headFeatureRenderer = ((LivingEntityRendererAccessor) this).getLayers()
			.stream()
			.filter(item -> item instanceof CustomHeadLayer)
			.findFirst();
		headFeatureRenderer.ifPresentOrElse(
			featureRenderer -> ((BipedLayerExtensions) featureRenderer).foolsBarrel$setIsBiped(true),
			() -> Logger.getLogger("minecraft").log(Level.INFO, "Um there is no head renderer"));
	}

	@Inject(method = "renderNameTag(Lnet/minecraft/client/player/AbstractClientPlayer;Lnet/minecraft/network/chat/Component;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;I)V", at = @At("HEAD"), cancellable = true)
	void foolsBarrel$renderNameTag(final AbstractClientPlayer clientPlayer,
								   final Component component,
								   final PoseStack poseStack,
								   final MultiBufferSource buffer,
								   final int i,
								   final CallbackInfo ci) {
		// Hide player nametag if they are crouching :3
		if (clientPlayer.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL) && clientPlayer.isCrouching()) {
			ci.cancel();
		}
	}

}
