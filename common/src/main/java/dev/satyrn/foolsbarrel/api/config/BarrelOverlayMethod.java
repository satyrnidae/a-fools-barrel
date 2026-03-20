package dev.satyrn.foolsbarrel.api.config;

import dev.satyrn.lepidoptera.annotations.YamlComment;

public enum BarrelOverlayMethod {
	@YamlComment("The barrel overlay will not render at all.")
	DISABLED,
	@YamlComment("""
		The barrel overlay will render pinned in space vertically; that is, it will move with the camera when looking left and right, but not up and down.
		This matches the original functionality from the April Fools snapshot.""") PIN_VERTICALLY,
	@YamlComment("The barrel overlay is pinned to the player's camera, acting identically to the pumpkin overlay.")
	MOVE_WITH_LOOK
}
