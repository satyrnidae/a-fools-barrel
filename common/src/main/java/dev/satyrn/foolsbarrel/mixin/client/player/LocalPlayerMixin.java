package dev.satyrn.foolsbarrel.mixin.client.player;

import com.mojang.authlib.GameProfile;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.item.Items;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Unique
@Environment(EnvType.CLIENT)
@Mixin(LocalPlayer.class)
public abstract class LocalPlayerMixin extends AbstractClientPlayer {
	LocalPlayerMixin(final ClientLevel clientLevel,
					 final GameProfile gameProfile,
					 final @Nullable ProfilePublicKey profilePublicKey) {
		super(clientLevel, gameProfile, profilePublicKey);
		throw new AssertionError();
	}

	@Inject(method = "aiStep()V", at = @At("TAIL"))
	void foolsBarrel$aiStep(final CallbackInfo ci) {
		if (this.getItemBySlot(EquipmentSlot.HEAD).is(Items.BARREL) && this.isCrouching()) {
			this.setPos(Mth.floor(this.getX()) + 0.5D, this.getY(), Mth.floor(this.getZ()) + 0.5D);
		}
	}
}
