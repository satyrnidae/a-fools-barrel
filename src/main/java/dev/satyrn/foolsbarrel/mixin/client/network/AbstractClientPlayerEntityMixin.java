package dev.satyrn.foolsbarrel.mixin.client.network;

import dev.satyrn.foolsbarrel.mixin.entity.player.PlayerEntityMixin;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(AbstractClientPlayerEntity.class)
public abstract class AbstractClientPlayerEntityMixin extends PlayerEntityMixin { }
