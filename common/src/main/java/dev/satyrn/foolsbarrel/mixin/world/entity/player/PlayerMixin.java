package dev.satyrn.foolsbarrel.mixin.world.entity.player;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
	@Shadow
	public abstract ItemStack getItemBySlot(EquipmentSlot slot);

	@Unique private @Nullable Pose foolsBarrel$previousPose;

	protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
		super(entityType, level);
	}

	@Inject(method = "updatePlayerPose()V", at = @At("HEAD"))
	public void foolsBarrel$setPreviousPose(final CallbackInfo ci) {
		this.foolsBarrel$previousPose = this.getPose();
	}

	@Inject(method = "updatePlayerPose()V", at = @At("TAIL"))
	public void foolsBarrel$updatePlayerPose(final CallbackInfo ci) {
		final Pose pose = this.getPose();
		if (this.foolsBarrel$previousPose != pose && this.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL)) {
			if (this.foolsBarrel$previousPose == Pose.CROUCHING) {
				this.level.playSound(null, this, SoundEvents.BARREL_OPEN, SoundSource.BLOCKS, 1, 1);
			} else {
				this.level.playSound(null, this, SoundEvents.BARREL_CLOSE, SoundSource.BLOCKS, 1, 1);
			}
		}
	}
}
