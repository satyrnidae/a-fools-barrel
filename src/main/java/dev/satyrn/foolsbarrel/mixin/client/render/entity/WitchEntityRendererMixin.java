package dev.satyrn.foolsbarrel.mixin.client.render.entity;

import dev.satyrn.foolsbarrel.client.BarrelFeatureRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.WitchEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings({"rawtypes","unchecked"})
@Mixin(WitchEntityRenderer.class)
public abstract class WitchEntityRendererMixin extends LivingEntityRendererMixin {

    @Inject(method = "<init> (Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;)V",
			at = @At("RETURN"))
    public void a_fools_barrel$constructor$onReturn(EntityRendererFactory.Context context,
													CallbackInfo ci) {
        // Add the barrel feature
        this.addFeature(new BarrelFeatureRenderer((FeatureRendererContext) this));
    }

}
