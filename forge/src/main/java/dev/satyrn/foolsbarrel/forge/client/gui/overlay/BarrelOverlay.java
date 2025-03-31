package dev.satyrn.foolsbarrel.forge.client.gui.overlay;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.satyrn.foolsbarrel.api.extensions.client.gui.GuiExtensions;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.gui.overlay.ForgeGui;
import net.minecraftforge.client.gui.overlay.IGuiOverlay;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class BarrelOverlay implements IGuiOverlay {
	@Override
	public void render(final ForgeGui forgeGui, final PoseStack arg, final float f, final int i, final int j) {
		if (!forgeGui.getMinecraft().options.hideGui &&
			forgeGui.getMinecraft().options.getCameraType().isFirstPerson()) {
			final @Nullable var player = forgeGui.getMinecraft().player;
			if (player != null && player.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL) && !player.isScoping()) {
				// Fabric / Quilt implementations add this mixin, which is useless to Forge w/o this call
				((GuiExtensions) forgeGui).foolsBarrel$renderBarrelOverlay();
			}
		}
	}
}
