package dev.satyrn.foolsbarrel.mixin.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.architectury.injectables.targets.ArchitecturyTarget;
import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.foolsbarrel.api.config.BarrelOverlayMethod;
import dev.satyrn.foolsbarrel.api.extensions.client.gui.GuiExtensions;
import dev.satyrn.foolsbarrel.data.tags.ModItemTags;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Unique
@Environment(EnvType.CLIENT)
@Mixin(Gui.class)
@Implements({
	@Interface(iface = GuiExtensions.class, prefix = "foolsBarrelX$")
})
public abstract class GuiMixin {
	@Unique private static final ResourceLocation FOOLS_BARREL$BARREL_EYE_HOLES_LOCATION =
		ResourceLocation.fromNamespaceAndPath(FoolsBarrelCommon.MOD_ID, "textures/misc/barrel_eye_holes.png");

	@Shadow @Final private Minecraft minecraft;

	@Inject(method = "render(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/DeltaTracker;)V", at = @At("HEAD"))
	void foolsBarrel$render(final GuiGraphics guiGraphics,
							final DeltaTracker deltaTracker,
							final @Nullable CallbackInfo ci) {
		// Not used by NeoForge. See dev.satyrn.foolsbarrel.neoforge.client.gui.overlay.BarrelOverlay
		if (!"neoforge".equalsIgnoreCase(ArchitecturyTarget.getCurrentTarget())) {
			final @Nullable var player = this.minecraft.player;
			if (player != null && player.getItemBySlot(EquipmentSlot.HEAD).is(ModItemTags.BARRELS)) {
				this.foolsBarrelX$renderBarrelOverlay(FoolsBarrelCommon.getClientConfig().getOverlayMethod(), guiGraphics);
			}
		}
	}

	@Unique
	public void foolsBarrelX$renderBarrelOverlay(BarrelOverlayMethod overlayMethod, GuiGraphics guiGraphics) {
		final var player = this.minecraft.player;
		if (player == null || overlayMethod == BarrelOverlayMethod.foolsbarrel$overlay$disabled) {
			return;
		}
		if (!player.getItemBySlot(EquipmentSlot.HEAD).is(ModItemTags.BARRELS) || player.isScoping()) {
			return;
		}
		int screenWidth = guiGraphics.guiWidth();
		int screenHeight = guiGraphics.guiHeight();
		float f = (float) Math.min(screenWidth, screenHeight);
		float k = ((float) screenWidth - f) / 2.0f;
		float l = ((float) screenHeight - f) / 2.0f;
		if (overlayMethod == BarrelOverlayMethod.foolsbarrel$overlay$pin_vertically) {
			l -= player.xRotO * 4.0f;
		}
		float m = k + f;
		float n = l + f;

		RenderSystem.disableDepthTest();
		RenderSystem.depthMask(false);
		RenderSystem.enableBlend();
		RenderSystem.defaultBlendFunc();

		// Draw barrel eye-holes texture scaled to fit the centered square
		guiGraphics.blit(FOOLS_BARREL$BARREL_EYE_HOLES_LOCATION,
			(int) k, (int) l,
			0.0f, 0.0f,
			(int) f, (int) f,
			256, 256);

		// Draw four black letterbox bars around the square
		guiGraphics.fill(0,       (int) n, screenWidth, screenHeight, 0xFF000000); // bottom
		guiGraphics.fill(0,       0,       screenWidth, (int) l,      0xFF000000); // top
		guiGraphics.fill(0,       (int) l, (int) k,     (int) n,      0xFF000000); // left
		guiGraphics.fill((int) m, (int) l, screenWidth, (int) n,      0xFF000000); // right

		RenderSystem.disableBlend();
		RenderSystem.depthMask(true);
		RenderSystem.enableDepthTest();
	}
}
