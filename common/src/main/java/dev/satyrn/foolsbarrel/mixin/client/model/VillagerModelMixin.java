package dev.satyrn.foolsbarrel.mixin.client.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.VillagerHeadModel;
import net.minecraft.client.model.VillagerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartNames;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.monster.Witch;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.VillagerDataHolder;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Unique
@Environment(EnvType.CLIENT)
@SuppressWarnings("rawtypes")
@Mixin(VillagerModel.class)
public abstract class VillagerModelMixin extends HierarchicalModel implements HeadedModel, VillagerHeadModel {
	@Shadow @Final private ModelPart root;
	@Shadow @Final private ModelPart head;
	@Shadow @Final private ModelPart hat;

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/Entity;FFFFF)V", at = @At("HEAD"))
	void foolsBarrel$setupAnim$head(final Entity entity,
									final float limbSwing,
									final float limbSwingAmount,
									final float ageInTicks,
									final float netHeadYaw,
									final float headPitch,
									final CallbackInfo ci) {
		this.hat.y = entity instanceof Witch ? -10.03125f : this.head.y;
		boolean wearingBarrel;
		if (entity instanceof final AbstractVillager merchant) {
			wearingBarrel = merchant.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL);
			this.root.getChild(PartNames.ARMS).visible = !wearingBarrel;
			this.head.visible = !wearingBarrel;
			if (wearingBarrel) {
				this.hat.y = 1.5f;
				if (entity instanceof final VillagerDataHolder villagerData &&
					villagerData.getVillagerData().getProfession() == VillagerProfession.LIBRARIAN) {
					this.hat.y += 1.8f;
				}
			}
		} else if (entity instanceof final Witch witch) {
			wearingBarrel = witch.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL);
			this.root.getChild(PartNames.ARMS).visible = !wearingBarrel;
			if (wearingBarrel) {
				this.hat.y = -13.03125f;
				this.head.y = 6.96875f;
			} else {
				this.head.y = 0.0f;
			}
		}
	}

	@Inject(method = "setupAnim(Lnet/minecraft/world/entity/Entity;FFFFF)V", at = @At("TAIL"))
	void foolsBarrel$setupAnim$tail(final Entity entity,
									final float limbAngle,
									final float limbDistance,
									final float animationProgress,
									final float headYaw,
									final float headPitch,
									final CallbackInfo ci) {
		boolean wearingBarrel = false;
		if (entity instanceof final AbstractVillager merchant) {
			wearingBarrel = merchant.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL);
		} else if (entity instanceof final Witch witch) {
			wearingBarrel = witch.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL);
		}
		if (wearingBarrel) {
			this.head.xRot = 0.0f;
			this.head.yRot = 0.0f;
		}
	}
}
