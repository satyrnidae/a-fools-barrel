package dev.satyrn.foolsbarrel.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Items;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntityMixin {
    @Shadow
    public abstract boolean isInSneakingPose();

    @Inject(method = "tickMovement", at = @At("TAIL"))
    public void onTickMovement(CallbackInfo ci) {
        if (this.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.BARREL) && this.isInSneakingPose()) {
            this.setPosition(MathHelper.floor(this.getX()) + 0.5D, this.getY(), MathHelper.floor(this.getZ()) + 0.5D);
        }
    }
}
