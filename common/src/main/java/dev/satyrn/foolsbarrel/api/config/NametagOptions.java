package dev.satyrn.foolsbarrel.api.config;

import dev.satyrn.lepidoptera.annotations.YamlComment;

/**
 * Defines rendering rules for player nametags when wearing a barrel.
 */
public enum NametagOptions {
	/**
	 * If selected, player nametags will never be hidden while the player is disguised.
	 */
	@YamlComment("Player nametags will always be visible, regardless of whether they are wearing a barrel.")
	NEVER,
	/**
	 * If selected, player nametags will only disappear while the player is crouching.
	 */
	@YamlComment("Player nametags will only be hidden while they are sneaking in a barrel.")
	WHEN_CROUCHED,
	/**
	 * If selected, player nametags will always be hidden so long as they are wearing a barrel.
	 * This matches the functionality of barrel disguises from the 2022 April Fool's snapshot.
	 */
	@YamlComment("Player nametags will be hidden at all times while they are wearing a barrel, regardless of whether they are hiding.")
	ALWAYS
}
