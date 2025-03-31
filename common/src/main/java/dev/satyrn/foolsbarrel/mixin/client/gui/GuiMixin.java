package dev.satyrn.foolsbarrel.mixin.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.foolsbarrel.api.extensions.client.gui.GuiExtensions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import javax.annotation.Nullable;

@Unique
@Environment(EnvType.CLIENT)
@Mixin(Gui.class)
public abstract class GuiMixin extends GuiComponent implements GuiExtensions {
	@Unique private static final ResourceLocation FOOLS_BARREL$BARREL_EYE_HOLES_LOCATION = new ResourceLocation(
		FoolsBarrelCommon.MOD_ID, "textures/misc/barrel_eye_holes.png");

	@Shadow private int screenWidth;
	@Shadow private int screenHeight;

	@Shadow @Final private Minecraft minecraft;

	// Suppressed warning due to incorrect INVOKE_ASSIGN handling
	@SuppressWarnings("InvalidInjectorMethodSignature")
	@Inject(method = "render", at = @At(value = "INVOKE_ASSIGN", target = "net/minecraft/world/entity/player/Inventory.getArmor (I)Lnet/minecraft/world/item/ItemStack;", ordinal = 0), locals = LocalCapture.CAPTURE_FAILSOFT)
	void foolsBarrel$render(final PoseStack poseStack,
							final float partialTick,
							final @Nullable CallbackInfo ci,
							final Font font,
							final float f,
							final ItemStack itemStack) {
		// Not used by Forge. See dev.satyrn.foolsbarrel.forge.client.gui.overlay.BarrelOverlay
		if (itemStack.is(Items.BARREL)) {
			this.foolsBarrel$renderBarrelOverlay();
		}
	}

	@Unique
	@Override
	public void foolsBarrel$renderBarrelOverlay() {
		if (this.minecraft.player == null) {
			return;
		}
		float f;
		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.defaultBlendFunc();
		RenderSystem.setShader(GameRenderer::getPositionTexShader);
		RenderSystem.setShaderTexture(0, FOOLS_BARREL$BARREL_EYE_HOLES_LOCATION);
		Tesselator tesselator = Tesselator.getInstance();
		BufferBuilder bufferBuilder = tesselator.getBuilder();
		float g = f = (float)Math.min(this.screenWidth, this.screenHeight);
		float h = Math.min((float)this.screenWidth / f, (float)this.screenHeight / g);
		float i = f * h;
		float j = g * h;
		float k = ((float)this.screenWidth - i) / 2.0f;
		float l = ((float)this.screenHeight - j) / 2.0f - this.minecraft.player.xRotO * 4.0f;
		float m = k + i;
		float n = l + j;
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_TEX);
		bufferBuilder.vertex(k, n, -90.0).uv(0.0f, 1.0f).endVertex();
		bufferBuilder.vertex(m, n, -90.0).uv(1.0f, 1.0f).endVertex();
		bufferBuilder.vertex(m, l, -90.0).uv(1.0f, 0.0f).endVertex();
		bufferBuilder.vertex(k, l, -90.0).uv(0.0f, 0.0f).endVertex();
		tesselator.end();
		RenderSystem.setShader(GameRenderer::getPositionColorShader);
		RenderSystem.disableTexture();
		bufferBuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
		bufferBuilder.vertex(0.0, this.screenHeight, -90.0).color(0, 0, 0, 255).endVertex();
		bufferBuilder.vertex(this.screenWidth, this.screenHeight, -90.0).color(0, 0, 0, 255).endVertex();
		bufferBuilder.vertex(this.screenWidth, n, -90.0).color(0, 0, 0, 255).endVertex();
		bufferBuilder.vertex(0.0, n, -90.0).color(0, 0, 0, 255).endVertex();
		bufferBuilder.vertex(0.0, l, -90.0).color(0, 0, 0, 255).endVertex();
		bufferBuilder.vertex(this.screenWidth, l, -90.0).color(0, 0, 0, 255).endVertex();
		bufferBuilder.vertex(this.screenWidth, 0.0, -90.0).color(0, 0, 0, 255).endVertex();
		bufferBuilder.vertex(0.0, 0.0, -90.0).color(0, 0, 0, 255).endVertex();
		bufferBuilder.vertex(0.0, n, -90.0).color(0, 0, 0, 255).endVertex();
		bufferBuilder.vertex(k, n, -90.0).color(0, 0, 0, 255).endVertex();
		bufferBuilder.vertex(k, l, -90.0).color(0, 0, 0, 255).endVertex();
		bufferBuilder.vertex(0.0, l, -90.0).color(0, 0, 0, 255).endVertex();
		bufferBuilder.vertex(m, n, -90.0).color(0, 0, 0, 255).endVertex();
		bufferBuilder.vertex(this.screenWidth, n, -90.0).color(0, 0, 0, 255).endVertex();
		bufferBuilder.vertex(this.screenWidth, l, -90.0).color(0, 0, 0, 255).endVertex();
		bufferBuilder.vertex(m, l, -90.0).color(0, 0, 0, 255).endVertex();
		tesselator.end();
		RenderSystem.enableTexture();
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
		RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
	}
}
