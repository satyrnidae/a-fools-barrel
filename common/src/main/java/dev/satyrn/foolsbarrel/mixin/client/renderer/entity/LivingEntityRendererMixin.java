package dev.satyrn.foolsbarrel.mixin.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Unique
@Environment(EnvType.CLIENT)
@SuppressWarnings({"rawtypes"})
@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin extends EntityRenderer implements RenderLayerParent {
	LivingEntityRendererMixin(final EntityRendererProvider.Context context) {
		super(context);
		throw new AssertionError();
	}

	@Inject(method = "setupRotations", at = @At("HEAD"), cancellable = true)
	void foolsBarrel$setupRotations(final LivingEntity entityLiving,
									final PoseStack poseStack,
									final float ageInTicks,
									final float rotationYaw,
									final float partialTicks,
									final CallbackInfo ci) {
		if (entityLiving.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL) && entityLiving.isCrouching()) {
			ci.cancel();
		}
	}
}
