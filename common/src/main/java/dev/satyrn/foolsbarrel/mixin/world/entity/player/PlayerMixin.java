package dev.satyrn.foolsbarrel.mixin.world.entity.player;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.foolsbarrel.api.extensions.world.entity.player.PlayerExtensions;
import dev.satyrn.foolsbarrel.data.tags.ModItemTags;
import dev.satyrn.foolsbarrel.inventory.BarrelChestMenu;
import dev.satyrn.foolsbarrel.inventory.PlayerBarrelContainer;
import dev.satyrn.foolsbarrel.network.BarrelInventoryTracker;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.entity.EntityDimensions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.UUID;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
	@Shadow @Final @Nullable Inventory inventory;
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
				this.level().playSound(null, this, SoundEvents.BARREL_OPEN, SoundSource.BLOCKS, 1, 1);
				if (!this.level().isClientSide()) {
					((PlayerExtensions) this).setBarrelFacing(Direction.UP);
				}
			} else if (pose == Pose.CROUCHING) {
				this.level().playSound(null, this, SoundEvents.BARREL_CLOSE, SoundSource.BLOCKS, 1, 1);
				if (!this.level().isClientSide() && FoolsBarrelCommon.getCommonConfig().getRandomRotateBarrel()) {
					((PlayerExtensions) this).setBarrelFacing(Direction.getRandom(this.getRandom()));
				}
			}
		}
	}

	@Inject(method = "getDefaultDimensions(Lnet/minecraft/world/entity/Pose;)Lnet/minecraft/world/entity/EntityDimensions;", at = @At("RETURN"), cancellable = true)
	void foolsBarrel$getDefaultDimensions(final Pose pose,
										  final CallbackInfoReturnable<EntityDimensions> cir) {
		if (pose == Pose.CROUCHING &&
			this.inventory != null &&
			this.getItemBySlot(EquipmentSlot.HEAD).is(ModItemTags.BARRELS)) {
			cir.setReturnValue(cir.getReturnValue().withEyeHeight(0.667F));
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

	@Inject(method = "interactOn", at = @At("HEAD"), cancellable = true)
	void foolsBarrel$interactOn(final Entity entity, final InteractionHand hand,
								final CallbackInfoReturnable<InteractionResult> cir) {
		if (hand != InteractionHand.MAIN_HAND) return;
		if (!(entity instanceof Player target)) return;
		if (!target.isCrouching()) return;
		if (!target.getItemBySlot(EquipmentSlot.HEAD).is(ModItemTags.BARRELS)) return;
		if (!FoolsBarrelCommon.getCommonConfig().getAllowHidingPlayerInventory()) return;

		if (!this.level().isClientSide()) {
			final PlayerBarrelContainer container = new PlayerBarrelContainer(target);
			final UUID targetId = target.getUUID();
			((Player) (Object) this).openMenu(new SimpleMenuProvider(
				(id, inv, p) -> new BarrelChestMenu(id, inv, container, targetId),
				Items.BARREL.getDescription()
			));
			//noinspection RedundantCast,DataFlowIssue
			BarrelInventoryTracker.track(((Player) (Object) this).getUUID(), targetId);
			((PlayerExtensions) target).setBarrelOpen(true);
			this.level().playSound(null, target, SoundEvents.BARREL_OPEN, SoundSource.BLOCKS, 1, 1);
		}
		cir.setReturnValue(InteractionResult.sidedSuccess(this.level().isClientSide()));
	}
}
