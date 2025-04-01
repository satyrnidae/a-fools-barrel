package dev.satyrn.foolsbarrel.mixin.world.entity.player;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
	@Shadow
	public abstract ItemStack getItemBySlot(EquipmentSlot slot);

	@Shadow @Final private @Nullable Inventory inventory;

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

	@Inject(method = "getStandingEyeHeight(Lnet/minecraft/world/entity/Pose;Lnet/minecraft/world/entity/EntityDimensions;)F", at = @At("HEAD"), cancellable = true)
	void setFoolsBarrel$getStandingEyeHeight(final Pose pose,
											 final EntityDimensions dimensions,
											 final CallbackInfoReturnable<Float> cir) {
		if (pose == Pose.CROUCHING && this.inventory != null && this.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL)) {
			cir.setReturnValue(0.667F);
			cir.cancel();
		}
	}

	@Override
	protected AABB makeBoundingBox() {
		if (this.isCrouching() && this.inventory != null && this.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL)) {
			return new AABB(this.getX() - 0.5F, this.getY(), this.getZ() - 0.5F, this.getX() + 0.5F, this.getY() + 1.0F, this.getZ() + 0.5F);
		}
		return super.makeBoundingBox();
	}

	@Override
	public void push(Entity entity) {
		if (this.isCrouching() && this.inventory != null && this.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL)) {
			return;
		}
		super.push(entity);
	}

	@Unique
	@Override
	public boolean canBeCollidedWith() {
		if (this.inventory != null && this.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL) && this.isCrouching()) {
			return true;
		}
		return super.canBeCollidedWith();
	}
}
