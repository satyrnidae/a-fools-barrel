package dev.satyrn.foolsbarrel.client;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public class FoolsBarrelClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // Really don't need to do nothin'
    }
}
