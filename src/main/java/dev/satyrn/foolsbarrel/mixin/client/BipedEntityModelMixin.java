package foolsbarrel.mixin.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.EndermanEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(BipedEntityModel.class)
public abstract class BipedEntityModelMixin {
    @Shadow @Final public ModelPart hat;

    @Accessor
    public abstract ModelPart getRightArm();

    @Accessor
    public abstract ModelPart getLeftArm();

    @Accessor
    public abstract ModelPart getHead();

    @Accessor
    public abstract ModelPart getBody();

    @Accessor
    public abstract ModelPart getLeftLeg();

    @Accessor
    public abstract ModelPart getRightLeg();


    @Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At("HEAD"))
    public void setAngles(LivingEntity livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        if (!(livingEntity instanceof EndermanEntity) && !(livingEntity instanceof ArmorStandEntity)) {
            boolean isBarrelEquipped = livingEntity.getEquippedStack(EquipmentSlot.HEAD).isOf(Items.BARREL);
            this.getLeftArm().visible = this.getRightArm().visible = this.getHead().visible = this.hat.visible = !isBarrelEquipped;
            if (livingEntity instanceof final PlayerEntity player && player.isInSneakingPose()) {
                this.getBody().visible = this.getRightLeg().visible = this.getLeftLeg().visible = !isBarrelEquipped;
            }
        }
    }
}
