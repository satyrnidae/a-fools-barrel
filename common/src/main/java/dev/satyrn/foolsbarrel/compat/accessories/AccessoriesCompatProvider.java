package dev.satyrn.foolsbarrel.compat.accessories;

import dev.satyrn.lepidoptera.api.compatibility.CompatibilityProvider;

/**
 * Optional Accessories mod compatibility provider.
 * Currently, a no-op; retained as the hook point for any future Accessories integration.
 */
@SuppressWarnings("unused")
public final class AccessoriesCompatProvider extends CompatibilityProvider {

    @Override
    public String getModId() {
        return "accessories";
    }
}
