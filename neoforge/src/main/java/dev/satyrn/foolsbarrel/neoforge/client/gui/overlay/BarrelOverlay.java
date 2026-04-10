package dev.satyrn.foolsbarrel.neoforge.client.gui.overlay;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.foolsbarrel.api.config.BarrelOverlayMethod;
import dev.satyrn.foolsbarrel.api.extensions.client.gui.GuiExtensions;
import dev.satyrn.foolsbarrel.data.tags.ModItemTags;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.LayeredDraw;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import javax.annotation.Nullable;

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

		@Nullable var player = minecraft.player;
		if (player == null || !player.getItemBySlot(EquipmentSlot.HEAD).is(ModItemTags.BARRELS) || player.isScoping()) {
			return;
		}

		((GuiExtensions) minecraft.gui).renderBarrelOverlay(overlayMethod, guiGraphics);
	}
}
