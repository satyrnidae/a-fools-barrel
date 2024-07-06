package dev.satyrn.foolsbarrel.mixin.client.network;

import net.minecraft.block.Blocks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends AbstractClientPlayerEntityMixin {
    @Shadow
    public abstract boolean isInSneakingPose();

    @Inject(method = "tickMovement ()V",
			at = @At("TAIL"))
    public void a_fools_barrel$afterTickMovement(CallbackInfo ci) {
        if (this.getEquippedStack(EquipmentSlot.HEAD).isOf(Blocks.BARREL.asItem()) && this.isInSneakingPose()) {
            this.setPosition(MathHelper.floor(this.getX()) + 0.5D, this.getY(), MathHelper.floor(this.getZ()) + 0.5D);
        }
    }
}
