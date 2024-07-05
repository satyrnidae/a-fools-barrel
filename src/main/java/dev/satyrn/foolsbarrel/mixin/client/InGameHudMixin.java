package foolsbarrel.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.satyrn.foolsbarrel.FoolsBarrel;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.hud.in_game.InGameHud;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
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

    @Shadow private float spyglassScale;

    @Inject(method = "renderOverlays", at = @At(value = "INVOKE", target = "net.minecraft.client.network.ClientPlayerEntity.isUsingSpyglass()Z", ordinal = 0))
    public void onRenderOverlay(GuiGraphics graphics, DeltaTracker tracker, CallbackInfo ci) {
        if (this.client.player != null &&
                this.client.player.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.BARREL) &&
                !this.client.player.isUsingSpyglass()) {
            this.renderBarrelEyeHoles(graphics, this.spyglassScale);
        }
    }

    @Unique
    public void renderBarrelEyeHoles(GuiGraphics graphics, float scale) {
        float f = (float)Math.min(graphics.getScaledWindowWidth(), graphics.getScaledWindowHeight());
        float g = f;
        float h = Math.min((float)graphics.getScaledWindowWidth() / f, (float)graphics.getScaledWindowHeight() / g) * scale;
        int i = MathHelper.floor(f * h);
        int j = MathHelper.floor(g * h);
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
