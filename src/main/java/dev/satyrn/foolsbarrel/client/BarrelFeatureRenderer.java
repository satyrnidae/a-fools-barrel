package dev.satyrn.foolsbarrel.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.Direction;

/**
 * Renders the barrel feature.
 * @param <T> The entity type
 * @param <M> The entity model type
 */
@Environment(EnvType.CLIENT)
public class BarrelFeatureRenderer<T extends LivingEntity, M extends EntityModel<T>> extends FeatureRenderer<T, M> {
    /**
     * Initializes a new Barrel Feature Renderer
     * @param context The feature renderer context.
     */
    public BarrelFeatureRenderer(FeatureRendererContext<T, M> context) {
        super(context);
    }

    /**
     * Renders the feature.
     * @param matrixStack The GL Matrix Stack
     * @param vertexConsumerProvider The vertex consumer provider
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
    public void render(MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider,
                       int light,
                       T entity,
                       float limbAngle,
                       float limbDistance,
                       float tickDelta,
                       float animationProgress,
                       float headYaw,
                       float headPitch) {
        ItemStack itemStack = entity.getEquippedStack(EquipmentSlot.HEAD);
        if (!itemStack.isEmpty() && itemStack.isOf(Items.BARREL)) {
            BlockState blockState = Blocks.BARREL.getDefaultState();
            if (blockState.contains(Properties.OPEN)) {
                boolean playerSneaking = false;
                if (entity instanceof final PlayerEntity player) {
                    playerSneaking = player.isInSneakingPose();
                }
                blockState = blockState.with(Properties.OPEN, !playerSneaking);
            }

            if (blockState.contains(Properties.FACING)) {
                blockState = blockState.with(Properties.FACING, Direction.UP);
            }

            matrixStack.push();
            if (entity instanceof final PlayerEntity player && player.isInSneakingPose()) {
                matrixStack.scale(1.07F, 1.07F, 1.07F);
                matrixStack.translate(-0.5D, 0.28D, -0.5D);
            } else {
                matrixStack.translate(-0.5D, -0.25D, -0.5D);
            }

            MinecraftClient.getInstance()
                    .getBlockRenderManager()
                    .renderBlockAsEntity(blockState, matrixStack, vertexConsumerProvider, light,
                            OverlayTexture.DEFAULT_UV);
            matrixStack.pop();
        }
    }
}
