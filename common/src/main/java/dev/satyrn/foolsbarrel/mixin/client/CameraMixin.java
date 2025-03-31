package dev.satyrn.foolsbarrel.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Unique
@Environment(EnvType.CLIENT)
@Mixin(Camera.class)
public abstract class CameraMixin {

	@Shadow private Entity entity;

	@Shadow private float eyeHeightOld;

	@Shadow private float eyeHeight;

	@Shadow private boolean detached;

	@Inject(method = "tick()V", cancellable = true, at = @At("HEAD"))
	public void foolsbarrel$tick(final CallbackInfo ci) {
		if (this.entity instanceof final LivingEntity livingEntity &&
			livingEntity.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL)) {
			this.eyeHeightOld = this.eyeHeight;
			float offset = !this.detached && this.entity.isCrouching() ? 0.875f : this.entity.getEyeHeight();
			this.eyeHeight += (offset - this.eyeHeight) * 0.5f;
			ci.cancel();
		}
	}
}
