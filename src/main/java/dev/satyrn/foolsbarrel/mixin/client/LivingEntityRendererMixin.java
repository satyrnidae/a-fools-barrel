package dev.satyrn.foolsbarrel.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    @Shadow
    protected abstract boolean addFeature(FeatureRenderer feature);

    @Accessor
    public abstract List<FeatureRenderer> getFeatures();

    @Inject(method = "setupTransforms", at = @At("HEAD"), cancellable = true)
    public void onSetupTransforms(LivingEntity entity,
                                  MatrixStack matrices,
                                  float animationProgress,
                                  float bodyYaw,
                                  float tickDelta,
                                  float f,
                                  CallbackInfo ci) {
        if (entity.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.BARREL) && entity.isInSneakingPose()) {
            ci.cancel();
        }
    }
}
