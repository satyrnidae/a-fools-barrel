package dev.satyrn.foolsbarrel.mixin.client;

import dev.satyrn.foolsbarrel.client.BarrelFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.WitchEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(WitchEntityRenderer.class)
public abstract class WitchEntityRendererMixin extends LivingEntityRendererMixin {

    @Inject(method = "<init>", at = @At("RETURN"))
    public void onInit(EntityRendererFactory.Context context, CallbackInfo ci) {
        // Add the barrel feature
        this.addFeature(new BarrelFeatureRenderer((FeatureRendererContext) this));
    }

}
