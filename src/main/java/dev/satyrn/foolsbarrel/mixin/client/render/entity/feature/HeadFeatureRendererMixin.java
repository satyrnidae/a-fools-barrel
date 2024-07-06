package dev.satyrn.foolsbarrel.mixin.client.render.entity.feature;

import dev.satyrn.foolsbarrel.client.IIsBiped;
import net.minecraft.block.Blocks;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.HeadFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(HeadFeatureRenderer.class)
public abstract class HeadFeatureRendererMixin implements IIsBiped {
    @Unique
	public boolean a_fools_barrel$isBiped = false;

    @Inject(method = "render (Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V",
			at = @At("HEAD"),
			cancellable = true)
    public void a_fools_barrel$onRender(MatrixStack matrixStack,
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
        var itemStack = livingEntity.getEquippedStack(EquipmentSlot.HEAD);

        if (itemStack.isEmpty() || this.a_fools_barrel$getIsBiped() && itemStack.isOf(Blocks.BARREL.asItem())) {
            // We are rendering a biped with a barrel on their head
            ci.cancel();
        }
    }

	@Unique
    @Override
    public boolean a_fools_barrel$getIsBiped() {
        return this.a_fools_barrel$isBiped;
    }

	@Unique
    @Override
    public void a_fools_barrel$setIsBiped(boolean value) {
        this.a_fools_barrel$isBiped = value;
    }
}
