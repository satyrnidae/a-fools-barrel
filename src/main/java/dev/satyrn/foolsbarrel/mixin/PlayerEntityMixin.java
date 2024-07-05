package dev.satyrn.foolsbarrel.mixin;

import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntityMixin {
    @Unique
    private EntityPose previousPose;

    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);;

	@Shadow
	public abstract void playSound(SoundEvent event, SoundCategory category, float volume, float pitch);

	@Inject(method = "updatePose", at = @At(value = "INVOKE", target = "net/minecraft/entity/player/PlayerEntity.isFallFlying ()Z" ))
    public void beforeUpdatePose(CallbackInfo ci) {
        this.previousPose = this.getPose();
    }

    @SuppressWarnings("InvalidInjectorMethodSignature")
	@Inject(method = "updatePose", at = @At(value = "INVOKE", target = "net/minecraft/entity/player/PlayerEntity.setPose (Lnet/minecraft/entity/EntityPose;)V", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    public void afterUpdatePose(CallbackInfo ci, EntityPose targetPose, EntityPose resultPose) {
		if (this.previousPose != resultPose && this.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.BARREL) && !this.getWorld().isClient()) {
			if (this.previousPose == EntityPose.CROUCHING) {
				this.playSound(SoundEvents.BLOCK_BARREL_OPEN, SoundCategory.BLOCKS, 1.0F, 1.0F);
			} else {
				this.playSound(SoundEvents.BLOCK_BARREL_CLOSE, SoundCategory.BLOCKS, 1.0F, 1.0F);
			}
		}
    }
}
