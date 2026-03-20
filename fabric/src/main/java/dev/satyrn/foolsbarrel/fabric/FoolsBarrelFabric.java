package dev.satyrn.foolsbarrel.fabric;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import net.fabricmc.api.ModInitializer;

import dev.satyrn.foolsbarrel.fabriclike.FoolsBarrelFabricLike;

public final class FoolsBarrelFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        // Run the Fabric-like setup.
        FoolsBarrelFabricLike.init();
    }
}
