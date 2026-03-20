package dev.satyrn.foolsbarrel.mixin.world.entity.player;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.foolsbarrel.data.tags.ModItemTags;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
	@Shadow @Final private @Nullable Inventory inventory;
	@Unique private @Nullable Pose foolsBarrel$previousPose;

	protected PlayerMixin(EntityType<? extends LivingEntity> entityType, Level level) {
		super(entityType, level);
	}

	@Shadow
	public abstract ItemStack getItemBySlot(EquipmentSlot slot);

	@Inject(method = "updatePlayerPose()V", at = @At("HEAD"))
	void foolsBarrel$setPreviousPose(final @Nullable CallbackInfo ci) {
		this.foolsBarrel$previousPose = this.getPose();
	}

	@Inject(method = "updatePlayerPose()V", at = @At("TAIL"))
	void foolsBarrel$updatePlayerPose(final @Nullable CallbackInfo ci) {
		final Pose pose = this.getPose();
		if (this.foolsBarrel$previousPose != pose && this.getItemBySlot(EquipmentSlot.HEAD).is(ModItemTags.BARRELS)) {
			if (this.foolsBarrel$previousPose == Pose.CROUCHING) {
				this.level.playSound(null, this, SoundEvents.BARREL_OPEN, SoundSource.BLOCKS, 1, 1);
			} else {
				this.level.playSound(null, this, SoundEvents.BARREL_CLOSE, SoundSource.BLOCKS, 1, 1);
			}
		}
	}

	@Inject(method = "getStandingEyeHeight(Lnet/minecraft/world/entity/Pose;Lnet/minecraft/world/entity/EntityDimensions;)F", at = @At("HEAD"), cancellable = true)
	void foolsBarrel$getStandingEyeHeight(final Pose pose,
										  final EntityDimensions dimensions,
										  final CallbackInfoReturnable<Float> cir) {
		if (pose == Pose.CROUCHING &&
			this.inventory != null &&
			this.getItemBySlot(EquipmentSlot.HEAD).is(ModItemTags.BARRELS)) {
			cir.setReturnValue(0.667F);
			cir.cancel();
		}
	}

	@Inject(method = "getSpeed()F", at = @At("HEAD"), cancellable = true)
	void foolsBarrel$getSpeed(final CallbackInfoReturnable<Float> cir) {
		if (this.getPose() == Pose.CROUCHING && this.getItemBySlot(EquipmentSlot.HEAD).is(ModItemTags.BARRELS)) {
			cir.setReturnValue(0.0F);
			cir.cancel();
		}
	}

	@Inject(method = "jumpFromGround()V", at = @At("HEAD"), cancellable = true)
	void foolsBarrel$jumpFromGround(final CallbackInfo ci) {
		if (!FoolsBarrelCommon.getCommonConfig().getAllowJumping() &&
			this.getItemBySlot(EquipmentSlot.HEAD).is(ModItemTags.BARRELS) &&
			this.getPose() == Pose.CROUCHING) {
			ci.cancel();
		}
	}

	@Unique
	@Override
	protected AABB makeBoundingBox() {
		if (this.isCrouching() && this.inventory != null && this.getItemBySlot(EquipmentSlot.HEAD).is(ModItemTags.BARRELS)) {
			return new AABB(this.getX() - 0.5F, this.getY(), this.getZ() - 0.5F, this.getX() + 0.5F, this.getY() + 1.0F,
				this.getZ() + 0.5F);
		}
		return super.makeBoundingBox();
	}

	@Unique
	@Override
	public void push(final Entity entity) {
		if (this.isCrouching() && this.inventory != null && this.getItemBySlot(EquipmentSlot.HEAD).is(ModItemTags.BARRELS)) {
			return;
		}
		super.push(entity);
	}

	@Unique
	@Override
	public boolean canBeCollidedWith() {
		if (this.inventory != null && this.getItemBySlot(EquipmentSlot.HEAD).is(ModItemTags.BARRELS) && this.isCrouching()) {
			return true;
		}
		return super.canBeCollidedWith();
	}
}
