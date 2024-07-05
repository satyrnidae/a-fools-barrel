package foolsbarrel.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(Camera.class)
public abstract class CameraMixin {

    @Shadow private Entity focusedEntity;

    @Shadow private float lastCameraY;

    @Shadow private float cameraY;

    @Shadow private boolean thirdPerson;

    @Inject(method = "updateEyeHeight", cancellable = true, at = @At("HEAD"))
    public void onUpdateEyeHeight(CallbackInfo ci) {
        if (this.focusedEntity instanceof final LivingEntity livingEntity &&
                livingEntity.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.BARREL)) {
            this.lastCameraY = this.cameraY;
            float offset = !this.thirdPerson && this.focusedEntity.isInSneakingPose()
                    ? 0.875f
                    : this.focusedEntity.getStandingEyeHeight();
            this.cameraY += (offset - this.cameraY) * 0.5f;
            ci.cancel();
        }
    }
}
