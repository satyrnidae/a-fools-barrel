package foolsbarrel.mixin;

import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntityMixin {
    @Unique
    private EntityPose previousPose;
    @Unique
    private boolean swimmingPoseWouldNotCollide;

    @Shadow
    public abstract ItemStack getEquippedStack(EquipmentSlot slot);

    @Shadow
    protected abstract boolean canMoveIntoPose(EntityPose pose);

    @Inject(method = "updatePose", at = @At("HEAD"))
    public void beforeUpdatePose(CallbackInfo ci) {
        this.swimmingPoseWouldNotCollide = this.canMoveIntoPose(EntityPose.SWIMMING);
        this.previousPose = this.getPose();
    }

    @Inject(method = "updatePose", at = @At("TAIL"))
    public void afterUpdatePose(CallbackInfo ci) {
        if (this.swimmingPoseWouldNotCollide) {
            final EntityPose currentPose = this.getPose();
            if (this.previousPose != currentPose && this.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.BARREL)) {
                if (this.previousPose == EntityPose.CROUCHING) {
                    this.getWorld().playSoundFromEntity(null, (PlayerEntity) (Object) this, SoundEvents.BLOCK_BARREL_OPEN,
                            SoundCategory.BLOCKS, 1.0f, 1.0f);
                } else {
                    this.getWorld().playSoundFromEntity(null, (PlayerEntity) (Object) this, SoundEvents.BLOCK_BARREL_CLOSE,
                            SoundCategory.BLOCKS, 1.0f, 1.0f);
                }
            }
        }
    }
}
