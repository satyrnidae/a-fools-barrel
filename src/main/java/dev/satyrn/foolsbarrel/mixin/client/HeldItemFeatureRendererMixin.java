package foolsbarrel.mixin.client;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeldItemFeatureRenderer.class)
public abstract class HeldItemFeatureRendererMixin {

    @Inject(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At("HEAD"), cancellable = true)
    public void beforeRender(MatrixStack matrixStack,
                             VertexConsumerProvider vertexConsumerProvider,
                             int i,
                             LivingEntity livingEntity,
                             float f,
                             float g,
                             float h,
                             float j,
                             float k,
                             float l,
                             CallbackInfo ci) {
        if (livingEntity.isInSneakingPose() && livingEntity.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.BARREL)) {
            ci.cancel();
        }
    }
}
