package foolsbarrel.mixin.client;

import dev.satyrn.foolsbarrel.IHeadFeatureRendererExtensions;
import dev.satyrn.foolsbarrel.client.BarrelFeatureRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.BipedEntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Environment(EnvType.CLIENT)
@Mixin(BipedEntityRenderer.class)
public abstract class BipedEntityRendererMixin extends LivingEntityRendererMixin {

    @Inject(method = "<init>(Lnet/minecraft/client/render/entity/EntityRendererFactory$Context;Lnet/minecraft/client/render/entity/model/BipedEntityModel;FFFF)V", at = @At("RETURN"))
    public void onInit(EntityRendererFactory.Context ctx,
                       BipedEntityModel model,
                       float shadowRadius,
                       float scaleX,
                       float scaleY,
                       float scaleZ,
                       CallbackInfo ci) {
        // Add the barrel feature
        this.addFeature(new BarrelFeatureRenderer((FeatureRendererContext) this));

        // Find the extant head feature renderer and set the biped flag to true
        Optional<FeatureRenderer> headFeatureRenderer = this.getFeatures()
                .stream()
                .filter(item -> item instanceof HeadFeatureRenderer)
                .findFirst();
        headFeatureRenderer.ifPresent(
                featureRenderer -> ((IHeadFeatureRendererExtensions) featureRenderer).setIsBiped(true));
    }

}
