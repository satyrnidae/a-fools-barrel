package dev.satyrn.foolsbarrel.fabric.mixin.world.entity;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.NeutralMob;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Unique
@Mixin(EnderMan.class)
public abstract class EnderManMixin extends Monster implements NeutralMob {
	EnderManMixin(EntityType<? extends Monster> entityType, Level level) {
		super(entityType, level);
		throw new AssertionError();
	}

	@Inject(method = "isLookingAtMe(Lnet/minecraft/world/entity/player/Player;)Z", at = @At(value = "INVOKE", target = "net/minecraft/world/level/block/Block.asItem ()Lnet/minecraft/world/item/Item;", ordinal = 0), locals = LocalCapture.CAPTURE_FAILSOFT, cancellable = true)
	void foolsBarrel$isLookingAtMe(final Player player,
								   final CallbackInfoReturnable<Boolean> cir,
								   final ItemStack itemStack) {
		if (itemStack.is(Items.BARREL)) {
			cir.setReturnValue(false);
			cir.cancel();
		}
	}
}
