package dev.satyrn.foolsbarrel.mixin.client;

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

    @Inject(method = "setAngles(Lnet/minecraft/entity/LivingEntity;FFFFF)V", at = @At("TAIL"))
    public void onSetAngles(LivingEntity livingEntity, float f, float g, float h, float i, float j, CallbackInfo ci) {
        // These aren't being set right via the biped model hiding for some reason; setting them manually.
        this.ear.visible = this.getHead().visible;
        this.cloak.visible = this.getBody().visible;
        this.jacket.visible = this.getBody().visible;
        this.leftSleeve.visible = this.getLeftArm().visible;
        this.rightSleeve.visible = this.getRightArm().visible;
        this.leftPants.visible = this.getLeftLeg().visible;
        this.rightPants.visible = this.getRightLeg().visible;
    }
}
