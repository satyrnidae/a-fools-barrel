package dev.satyrn.foolsbarrel.api.extensions.client.gui;

import dev.satyrn.foolsbarrel.api.config.BarrelOverlayMethod;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;

@Environment(EnvType.CLIENT)
public interface GuiExtensions {
	void renderBarrelOverlay(BarrelOverlayMethod overlayMethod, GuiGraphics guiGraphics);
}
