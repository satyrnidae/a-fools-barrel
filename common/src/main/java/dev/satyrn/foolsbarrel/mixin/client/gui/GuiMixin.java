package dev.satyrn.foolsbarrel.mixin.client.gui;

import com.llamalad7.mixinextras.sugar.Local;
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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

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

	@Inject(method = "renderCameraOverlays(Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/DeltaTracker;)V",
			at = @At(value = "INVOKE_ASSIGN",
					 target = "net/minecraft/world/entity/player/Inventory.getArmor (I)Lnet/minecraft/world/item/ItemStack;"))
	void foolsBarrel$renderCameraOverlays(final GuiGraphics guiGraphics,
	                        final DeltaTracker deltaTracker,
	                        final @Nullable CallbackInfo ci,
	                        @SuppressWarnings("LocalMayUseName")
								@Local(index = 4) ItemStack itemStack) {
		// Neoforge is supposed to use render layers, so it doesn't use this injection.
		// See dev.satyrn.foolsbarrel.neoforge.client.gui.overlay.BarrelOverlay
		if (!"neoforge".equalsIgnoreCase(ArchitecturyTarget.getCurrentTarget())) {
			// Only render if the itemstack isn't a pumpkin (pumpkin overlay supersedes)
			if (itemStack.is(ModItemTags.BARRELS) && !itemStack.is(Blocks.CARVED_PUMPKIN.asItem())) {
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
