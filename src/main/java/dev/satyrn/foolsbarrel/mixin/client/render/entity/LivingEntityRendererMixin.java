package dev.satyrn.foolsbarrel.mixin.client.render.entity;

import net.minecraft.block.Blocks;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@SuppressWarnings("rawtypes")
@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {
    @Shadow
    protected abstract boolean addFeature(FeatureRenderer feature);

    @Accessor
    public abstract List<FeatureRenderer> getFeatures();

    @Inject(method = "setupTransforms (Lnet/minecraft/entity/LivingEntity;Lnet/minecraft/client/util/math/MatrixStack;FFFF)V",
			at = @At("HEAD"),
			cancellable = true)
    public void a_fools_barrel$onSetupTransforms(LivingEntity entity,
												 MatrixStack matrices,
												 float animationProgress,
												 float bodyYaw,
												 float tickDelta,
												 float f,
												 CallbackInfo ci) {
		var equippedStack = entity.getEquippedStack(EquipmentSlot.HEAD);
        if (equippedStack.isOf(Blocks.BARREL.asItem()) && entity.isInSneakingPose()) {
            ci.cancel();
        }
    }
}
