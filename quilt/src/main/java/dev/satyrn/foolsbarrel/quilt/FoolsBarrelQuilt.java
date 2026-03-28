package dev.satyrn.foolsbarrel.quilt;

import dev.satyrn.foolsbarrel.fabriclike.FoolsBarrelFabricLike;
import net.fabricmc.api.ModInitializer;

@SuppressWarnings("unused")
public final class FoolsBarrelQuilt implements ModInitializer {
	@Override
	public void onInitialize() {
		FoolsBarrelFabricLike.init();
	}
}
