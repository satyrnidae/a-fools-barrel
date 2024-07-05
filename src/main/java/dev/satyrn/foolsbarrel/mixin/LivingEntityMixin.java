package dev.satyrn.foolsbarrel.mixin;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends EntityMixin {
    @Inject(method = "getPreferredEquipmentSlot", at = @At("HEAD"), cancellable = true)
    private void onGetPreferredEquipmentSlot(ItemStack stack, CallbackInfoReturnable<EquipmentSlot> cir) {
        if (stack.isOf(Items.BARREL)) {
            cir.setReturnValue(EquipmentSlot.HEAD);
            cir.cancel();
        }
    }

    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);
}
