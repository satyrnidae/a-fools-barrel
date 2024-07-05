package dev.satyrn.foolsbarrel.mixin;

import net.minecraft.block.Blocks;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EndermanEntity.class)
public abstract class EndermanEntityMixin {
    @Inject(method = "isPlayerStaring", at = @At("HEAD"), cancellable = true)
    public void checkIsPlayerStaring(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        ItemStack itemStack = player.getInventory().armor.get(3);
        if (itemStack.isOf(Blocks.BARREL.asItem())) {
            cir.setReturnValue(false);
            cir.cancel();
        }
    }
}
