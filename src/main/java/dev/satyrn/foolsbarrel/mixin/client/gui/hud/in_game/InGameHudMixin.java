package dev.satyrn.foolsbarrel.mixin.client.gui.hud.in_game;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.satyrn.foolsbarrel.FoolsBarrel;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.hud.in_game.InGameHud;
import net.minecraft.client.render.DeltaTracker;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
	// Inject after we check the item in the player's head slot
    @Inject(method = "renderOverlays (Lnet/minecraft/client/gui/GuiGraphics;Lnet/minecraft/client/render/DeltaTracker;)V",
			at = @At(value = "INVOKE",
					 target = "net/minecraft/entity/player/PlayerInventory.getArmorStack (I)Lnet/minecraft/item/ItemStack;",
					 ordinal = 0,
				     shift = At.Shift.BY,
					 by = 2),
			locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    public void a_fools_barrel$renderOverlays$afterGetArmorStack(GuiGraphics graphics, DeltaTracker tracker, CallbackInfo ci, float method_60636, ItemStack equippedStack) {
        if (equippedStack.isOf(Blocks.BARREL.asItem())) {
            this.a_fools_barrel$renderBarrelOverlay(graphics);
        }
    }

	@Unique
	public void a_fools_barrel$renderBarrelOverlay(GuiGraphics graphics) {
		float f = (float)Math.min(graphics.getScaledWindowWidth(), graphics.getScaledWindowHeight());
		float h = Math.min((float)graphics.getScaledWindowWidth() / f, (float)graphics.getScaledWindowHeight() / f) * 1.5F;
		int i = MathHelper.floor(f * h);
		int j = MathHelper.floor(f * h);
		int k = (graphics.getScaledWindowWidth() - i) / 2;
		int l = (graphics.getScaledWindowHeight() - j) / 2;
		int m = k + i;
		int n = l + j;
		RenderSystem.enableBlend();
		graphics.drawTexture(FoolsBarrel.BARREL_EYE_HOLES, k, l, -90, 0.0F, 0.0F, i, j, i, j);
		RenderSystem.disableBlend();
		graphics.fill(RenderLayer.getGuiOverlay(), 0, n, graphics.getScaledWindowWidth(), graphics.getScaledWindowHeight(), -90, -16777216);
		graphics.fill(RenderLayer.getGuiOverlay(), 0, 0, graphics.getScaledWindowWidth(), l, -90, -16777216);
		graphics.fill(RenderLayer.getGuiOverlay(), 0, l, k, n, -90, -16777216);
		graphics.fill(RenderLayer.getGuiOverlay(), m, l, graphics.getScaledWindowWidth(), n, -90, -16777216);
	}
}
