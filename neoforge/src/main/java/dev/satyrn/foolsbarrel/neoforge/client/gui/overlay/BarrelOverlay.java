package dev.satyrn.foolsbarrel.neoforge.client.gui.overlay;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.foolsbarrel.api.config.BarrelOverlayMethod;
import dev.satyrn.foolsbarrel.api.extensions.client.gui.GuiExtensions;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class BarrelOverlay implements LayeredDraw.Layer {
	@Override
	public void render(final GuiGraphics guiGraphics, final DeltaTracker deltaTracker) {
		final Minecraft minecraft = Minecraft.getInstance();
		final BarrelOverlayMethod overlayMethod = FoolsBarrelCommon.getClientConfig().getOverlayMethod();
		if (overlayMethod == BarrelOverlayMethod.foolsbarrel$overlay$disabled || minecraft.options.hideGui ||
			!minecraft.options.getCameraType().isFirstPerson()) {
			return;
		}
		((GuiExtensions) minecraft.gui).renderBarrelOverlay(overlayMethod, guiGraphics);
	}
}
