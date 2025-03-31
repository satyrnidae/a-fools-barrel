package dev.satyrn.foolsbarrel.quilt;

import dev.satyrn.foolsbarrel.fabriclike.FoolsBarrelFabricLike;
import org.jetbrains.annotations.NotNull;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;

@SuppressWarnings("unused")
public final class FoolsBarrelQuilt implements ModInitializer {
	@Override
	public void onInitialize(final @NotNull ModContainer mod) {
		// Run the Fabric-like setup.
		FoolsBarrelFabricLike.init();
	}
}
