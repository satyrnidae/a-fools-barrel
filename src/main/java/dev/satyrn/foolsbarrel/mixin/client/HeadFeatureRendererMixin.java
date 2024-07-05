package dev.satyrn.foolsbarrel.mixin.client;

import dev.satyrn.foolsbarrel.IHeadFeatureRendererExtensions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(HeadFeatureRenderer.class)
public abstract class HeadFeatureRendererMixin implements IHeadFeatureRendererExtensions {
    public boolean isBiped = false;

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
        ItemStack itemStack = livingEntity.getEquippedStack(EquipmentSlot.HEAD);

        if (itemStack.isEmpty() || this.isBiped && itemStack.isOf(Items.BARREL)) {
            // We are rendering a biped with a barrel on their head
            ci.cancel();
        }
    }

    @Override
    public boolean getIsBiped() {
        return this.isBiped;
    }

    @Override
    public void setIsBiped(boolean value) {
        this.isBiped = value;
    }
}
