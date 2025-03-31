package dev.satyrn.foolsbarrel.client;

import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

/**
 * Renders the barrel feature.
 * @param <T> The entity type
 * @param <M> The entity model type
 */
@Environment(EnvType.CLIENT)
public class BarrelLayer<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {
    /**
     * Initializes a new Barrel Feature Renderer
     * @param context The feature renderer context.
     */
    public BarrelLayer(RenderLayerParent<T, M> context) {
        super(context);
    }

    /**
     * Renders the feature.
     * @param poseStack The GL Matrix Stack
     * @param buffer The vertex consumer provider
     * @param light The current light level
     * @param entity The entity to render
     * @param limbAngle The entity's limb angle
     * @param limbDistance The entity's limb distance
     * @param tickDelta The number of ticks since the last render
     * @param animationProgress The animation progress
     * @param headYaw The entity's head yaw
     * @param headPitch The entity's head pitch
     */
    @Override
    public void render(final PoseStack poseStack,
					   final MultiBufferSource buffer,
					   final int light,
					   final T entity,
					   final float limbAngle,
					   final float limbDistance,
					   final float tickDelta,
					   final float animationProgress,
					   final float headYaw,
					   final float headPitch) {
        ItemStack itemStack = entity.getItemBySlot(EquipmentSlot.HEAD);
        if (!itemStack.isEmpty() && itemStack.is(Items.BARREL)) {
            BlockState blockState = Blocks.BARREL.defaultBlockState();
            if (blockState.hasProperty(BlockStateProperties.OPEN)) {
                boolean playerSneaking = false;
                if (entity instanceof final Player player) {
                    playerSneaking = player.isCrouching();
                }
                blockState = blockState.setValue(BlockStateProperties.OPEN, !playerSneaking);
            }

            if (blockState.hasProperty(BlockStateProperties.FACING)) {
                blockState = blockState.setValue(BlockStateProperties.FACING, Direction.UP);
            }

            poseStack.pushPose();
            if (entity instanceof final Player player && player.isCrouching()) {
                poseStack.scale(1.07F, 1.07F, 1.07F);
                poseStack.translate(-0.5D, 0.28D, -0.5D);
            } else {
                poseStack.translate(-0.5D, -0.25D, -0.5D);
            }

            Minecraft.getInstance()
                    .getBlockRenderer()
                    .renderSingleBlock(blockState, poseStack, buffer, light,
                            OverlayTexture.NO_OVERLAY);
            poseStack.popPose();
        }
    }
}
