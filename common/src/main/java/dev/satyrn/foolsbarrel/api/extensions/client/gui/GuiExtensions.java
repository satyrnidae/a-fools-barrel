package dev.satyrn.foolsbarrel.api.extensions.client.gui;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface GuiExtensions {
	void foolsBarrel$renderBarrelOverlay();
}
