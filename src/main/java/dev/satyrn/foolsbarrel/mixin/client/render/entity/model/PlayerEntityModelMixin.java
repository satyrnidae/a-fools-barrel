package dev.satyrn.foolsbarrel.mixin.client.render.entity.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntityModel.class)
public abstract class PlayerEntityModelMixin extends BipedEntityModelMixin {
    @Shadow @Final public ModelPart jacket;
    @Shadow @Final public ModelPart leftSleeve;
    @Shadow @Final public ModelPart rightSleeve;
    @Shadow @Final public ModelPart rightPants;
    @Shadow @Final public ModelPart leftPants;
    @Shadow @Final private ModelPart ear;
    @Shadow @Final private ModelPart cloak;

    @Inject(method = "setAngles (Lnet/minecraft/entity/LivingEntity;FFFFF)V",
			at = @At("TAIL"))
    public void a_fools_barrel$afterSetAngles(LivingEntity livingEntity,
											  float f,
											  float g,
											  float h,
											  float i,
											  float j,
											  CallbackInfo ci) {
        // These aren't being set right via the biped model hiding for some reason; setting them manually.
        this.ear.visible = this.head.visible;
        this.cloak.visible = this.body.visible;
        this.jacket.visible = this.body.visible;
        this.leftSleeve.visible = this.leftArm.visible;
        this.rightSleeve.visible = this.rightArm.visible;
        this.leftPants.visible = this.leftLeg.visible;
        this.rightPants.visible = this.rightLeg.visible;
    }
}
