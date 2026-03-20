package dev.satyrn.foolsbarrel.api.extensions.client.renderer.entity.layers;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface BipedLayerExtensions {
    void setBiped(boolean value);
}
