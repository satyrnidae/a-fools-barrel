package dev.satyrn.foolsbarrel.mixin.client;

import dev.satyrn.foolsbarrel.IHeadFeatureRendererExtensions;
import dev.satyrn.foolsbarrel.client.BarrelFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Environment(EnvType.CLIENT)
@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRendererMixin {

    @Inject(method = "<init>", at = @At("RETURN"))
    public void onInit(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
        // Add the barrel feature
        this.addFeature(new BarrelFeatureRenderer((FeatureRendererContext) this));

        // Find the extant head feature renderer and set the biped flag to true
        Optional<FeatureRenderer> headFeatureRenderer = this.getFeatures()
                .stream()
                .filter(item -> item instanceof HeadFeatureRenderer)
                .findFirst();
        headFeatureRenderer.ifPresentOrElse(
                featureRenderer -> ((IHeadFeatureRendererExtensions) featureRenderer).setIsBiped(true),
                () -> Logger.getLogger("minecraft").log(Level.INFO, "Um there is no head renderer"));
    }

}
