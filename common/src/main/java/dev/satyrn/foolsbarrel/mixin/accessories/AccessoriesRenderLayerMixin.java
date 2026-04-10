package dev.satyrn.foolsbarrel.mixin.accessories;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.satyrn.foolsbarrel.data.tags.ModItemTags;
import io.wispforest.accessories.api.client.AccessoryRenderer;
import io.wispforest.accessories.api.slot.SlotGroup;
import io.wispforest.accessories.api.slot.SlotReference;
import io.wispforest.accessories.client.AccessoriesRenderLayer;
import io.wispforest.accessories.data.SlotGroupLoader;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Optional;

/**
 * Suppresses rendering of accessories in slots parented to the head, arms, and torso when
 * the entity is wearing a barrel, and additionally leg-slot accessories when crouching.
 */
@Environment(EnvType.CLIENT)
@Mixin(value = AccessoriesRenderLayer.class)
public abstract class AccessoriesRenderLayerMixin {

    @Redirect(
        method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V",
        at = @At(
            value = "INVOKE",
            target = "Lio/wispforest/accessories/api/client/AccessoryRenderer;render(" +
                "Lnet/minecraft/world/item/ItemStack;" +
                "Lio/wispforest/accessories/api/slot/SlotReference;" +
                "Lcom/mojang/blaze3d/vertex/PoseStack;" +
                "Lnet/minecraft/client/model/EntityModel;" +
                "Lnet/minecraft/client/renderer/MultiBufferSource;" +
                "IFFFFFF)V"
        )
    )
    private <M extends LivingEntity> void foolsBarrel$suppressBarrelAccessories(
            final AccessoryRenderer renderer,
            final ItemStack stack,
            final SlotReference ref,
            final PoseStack poseStack,
            final EntityModel<M> model,
            final MultiBufferSource bufferSource,
            final int light,
            final float limbSwing,
            final float limbSwingAmount,
            final float partialTicks,
            final float ageInTicks,
            final float netHeadYaw,
            final float headPitch) {

        final LivingEntity entity = ref.entity();

		if (entity.getItemBySlot(EquipmentSlot.HEAD).is(ModItemTags.BARRELS)) {
			final Optional<SlotGroup> group = SlotGroupLoader.INSTANCE.findGroup(true, ref.slotName());
			if (group.isPresent()) {
				final boolean suppress = switch(group.get().name()) {
					case "head", "arm", "chest" -> true;
					case "leg" -> entity.isCrouching();
					default -> false;
				};
				if (suppress) {
					return;
				}
			}
		}

		renderer.render(stack, ref, poseStack, model, bufferSource,
					light, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch);
    }
}
