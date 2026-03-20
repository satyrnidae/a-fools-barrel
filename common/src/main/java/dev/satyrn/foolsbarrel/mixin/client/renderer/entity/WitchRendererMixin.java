package dev.satyrn.foolsbarrel.mixin.client.renderer.entity;

import dev.satyrn.foolsbarrel.client.BarrelLayer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.WitchRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Unique
@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes", "unchecked"})
@Mixin(WitchRenderer.class)
public abstract class WitchRendererMixin extends MobRenderer {
	WitchRendererMixin(final EntityRendererProvider.Context context,
					   final EntityModel model,
					   final float shadowRadius) {
		super(context, model, shadowRadius);
		throw new AssertionError();
	}

	@Inject(method = "<init>", at = @At("RETURN"))
	public void foolsBarrel$init(final EntityRendererProvider.Context context, final CallbackInfo ci) {
		// Add the barrel feature
		this.addLayer(new BarrelLayer(this));
	}

}
