package dev.satyrn.foolsbarrel.mixin.client.renderer.entity;

import dev.satyrn.foolsbarrel.client.BarrelLayer;
import dev.satyrn.foolsbarrel.api.extensions.client.renderer.entity.layers.BipedLayerExtensions;
import dev.satyrn.foolsbarrel.mixin.client.accessors.renderer.entity.LivingEntityRendererAccessor;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.IllagerModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.IllagerRenderer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Unique
@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
@Mixin(IllagerRenderer.class)
public abstract class IllagerRendererMixin extends MobRenderer {
	IllagerRendererMixin(EntityRendererProvider.Context context, EntityModel model, float shadowRadius) {
		super(context, model, shadowRadius);
		throw new AssertionError();
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	void foolsBarrel$init(final EntityRendererProvider.Context ctx,
						  final IllagerModel model,
						  final float shadowRadius,
						  final CallbackInfo ci) {
		// Add the barrel feature
		this.addLayer(new BarrelLayer(this));

		// Find the extant head feature renderer and set the biped flag to true
		Optional<RenderLayer> headFeatureRenderer = ((LivingEntityRendererAccessor) this).getLayers()
			.stream()
			.filter(item -> item instanceof CustomHeadLayer)
			.findFirst();
		headFeatureRenderer.ifPresent(
			featureRenderer -> ((BipedLayerExtensions) featureRenderer).foolsBarrel$setIsBiped(true));
	}

}
