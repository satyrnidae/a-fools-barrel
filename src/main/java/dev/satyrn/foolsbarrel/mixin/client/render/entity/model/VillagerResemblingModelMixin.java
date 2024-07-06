package dev.satyrn.foolsbarrel.mixin.client.render.entity.model;

import net.minecraft.block.Blocks;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModelPartNames;
import net.minecraft.client.render.entity.model.VillagerResemblingModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.mob.WitchEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.village.VillagerDataContainer;
import net.minecraft.village.VillagerProfession;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(VillagerResemblingModel.class)
public abstract class VillagerResemblingModelMixin {
    @Shadow @Final private ModelPart root;
    @Shadow @Final private ModelPart head;
    @Shadow @Final private ModelPart hat;

    @Inject(method = "setAngles(Lnet/minecraft/entity/Entity;FFFFF)V",
			at = @At("HEAD"))
    public void a_fools_barrel$onSetAngles(Entity entity,
										   float limbAngle,
										   float limbDistance,
										   float animationProgress,
										   float headYaw,
										   float headPitch,
										   CallbackInfo ci) {
        this.hat.pivotY = entity instanceof WitchEntity ? -10.03125f : this.head.pivotY;
        boolean wearingBarrel;
        if (entity instanceof final MerchantEntity merchant) {
            wearingBarrel = merchant.getEquippedStack(EquipmentSlot.HEAD).isOf(Blocks.BARREL.asItem());
            this.root.getChild(EntityModelPartNames.ARMS).visible = !wearingBarrel;
            this.head.visible = !wearingBarrel;
            if (wearingBarrel) {
                this.hat.pivotY = 1.5f;
                if (entity instanceof final VillagerDataContainer villagerData &&
                        villagerData.getVillagerData().getProfession() == VillagerProfession.LIBRARIAN) {
                    this.hat.pivotY += 1.8f;
                }
            }
        } else if (entity instanceof final WitchEntity witch) {
            wearingBarrel = witch.getEquippedStack(EquipmentSlot.HEAD).isOf(Blocks.BARREL.asItem());
            this.root.getChild(EntityModelPartNames.ARMS).visible = !wearingBarrel;
            if (wearingBarrel) {
                this.hat.pivotY = -13.03125f;
                this.head.pivotY = 6.96875f;
            } else {
                this.head.pivotY = 0.0f;
            }
        }
    }

    @Inject(method = "setAngles(Lnet/minecraft/entity/Entity;FFFFF)V",
			at = @At("TAIL"))
    public void a_fools_barrel$afterSetAngles(Entity entity,
											  float limbAngle,
											  float limbDistance,
											  float animationProgress,
											  float headYaw,
											  float headPitch,
											  CallbackInfo ci) {
        boolean wearingBarrel = false;
        if (entity instanceof final MerchantEntity merchant) {
            wearingBarrel = merchant.getEquippedStack(EquipmentSlot.HEAD).isOf(Blocks.BARREL.asItem());
        } else if (entity instanceof final WitchEntity witch) {
            wearingBarrel = witch.getEquippedStack(EquipmentSlot.HEAD).isOf(Blocks.BARREL.asItem());
        }
        if (wearingBarrel) {
            this.head.pitch = 0.0f;
            this.head.yaw = 0.0f;
        }
    }
}
