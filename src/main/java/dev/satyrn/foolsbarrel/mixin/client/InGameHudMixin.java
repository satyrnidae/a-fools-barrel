package dev.satyrn.foolsbarrel.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.satyrn.foolsbarrel.FoolsBarrel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.hud.in_game.InGameHud;
import net.minecraft.client.render.DeltaTracker;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class InGameHudMixin {
    @Shadow @Final private MinecraftClient client;

	@Shadow private void renderOverlay(GuiGraphics graphics, Identifier texture, float opacity) { }

    @Inject(method = "renderOverlays", at = @At(value = "INVOKE", target = "net.minecraft.client.network.ClientPlayerEntity.isUsingSpyglass()Z", ordinal = 0))
    public void onRenderOverlay(GuiGraphics graphics, DeltaTracker tracker, CallbackInfo ci) {
        if (this.client.player != null &&
                this.client.player.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.BARREL) &&
                !this.client.player.isUsingSpyglass()) {
            this.renderOverlay(graphics, FoolsBarrel.BARREL_EYE_HOLES, 1.0F);
        }
    }
}
