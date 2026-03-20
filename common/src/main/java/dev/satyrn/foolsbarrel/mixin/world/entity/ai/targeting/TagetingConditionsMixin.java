package dev.satyrn.foolsbarrel.mixin.world.entity.ai.targeting;

import dev.satyrn.foolsbarrel.data.tags.ModItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(TargetingConditions.class)
public abstract class TagetingConditionsMixin {
	@Shadow @Final private boolean isCombat;

	@Inject(method = "test(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/world/entity/LivingEntity;)Z", at = @At("HEAD"), cancellable = true)
	void foolsBarrel$test(final @Nullable LivingEntity attacker,
						  final LivingEntity target,
						  final CallbackInfoReturnable<Boolean> cir) {
		// Ignore hiding players for new combat target selection.
		if (this.isCombat && target.isCrouching() && target.getItemBySlot(EquipmentSlot.HEAD).is(ModItemTags.BARRELS)) {
			cir.setReturnValue(false);
			cir.cancel();
		}
	}
}
