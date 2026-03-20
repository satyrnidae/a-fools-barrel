package dev.satyrn.foolsbarrel.forge.data.client;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.foolsbarrel.api.config.BarrelOverlayMethod;
import dev.satyrn.foolsbarrel.api.config.NametagOptions;
import dev.satyrn.foolsbarrel.network.ModPackets;
import dev.satyrn.foolsbarrel.sounds.FoolsBarrelSoundEvents;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModUSEnglishLanguageProvider extends LanguageProvider {
	public ModUSEnglishLanguageProvider(DataGenerator gen) {
		super(gen, FoolsBarrelCommon.MOD_ID, "en_us");
	}

	@Override
	public String getName() {
		return "A Fool's Barrel EN_US Language Provider";
	}

	@Override
	protected void addTranslations() {
		this.add("subtitles." + FoolsBarrelSoundEvents.BARREL_EQUIP_SOUND.getId().getPath(), "Barrel equipped");
		this.add(FoolsBarrelCommon.MOD_ID + ".tip.wearable_barrels", "Barrels can be worn on your head! Hide in them by crouching.");

		this.addConfigTitle("A Fool's Barrel - Options");

		this.addConfigCategory("client", "Client Options");
		this.addClientConfigOption("adjustCameraInBarrel", "Lower camera when barrel is worn?");
		this.addClientConfigOption("overlayMethod", "How should the barrel overlay display?");
		this.addClientConfigOption("hideNametag", "Hide player nametag while barrel is worn:");
		this.add(NametagOptions.ALWAYS.name(), "Always");
		this.add(NametagOptions.WHEN_CROUCHED.name(), "When crouched");
		this.add(NametagOptions.NEVER.name(), "Never");
		this.add(BarrelOverlayMethod.DISABLED.name(), "Disabled");
		this.add(BarrelOverlayMethod.PIN_VERTICALLY.name(), "Pin vertically");
		this.add(BarrelOverlayMethod.MOVE_WITH_LOOK.name(), "Move with look");

		this.addConfigCategory("common", "Common Options");
		this.addCommonConfigOption("snapHidingPlayersToGrid", "Snap players to voxel grid when they hide?");
		this.addCommonConfigOption("allowJumping", "Allow players in barrels to jump?");
		this.addCommonConfigOption("shouldBarrelHideSightline", "Should a barrel obscure sightline from Endermen?");
		this.addCommonConfigOption("shouldAnimalsIgnoreHidingPlayers", "Should animals not notice hidden players?");
		this.addCommonConfigOption("shouldHidingRemoveMobAggro", "Should hiding stop mobs from continuing an attack?");

		this.add(ModPackets.I18N_VERSION_MISMATCH, "Fools Barrel network version mismatch! Server version is {} while client version is {}.");
	}

	private void addConfig(String name, String...keys) {
		final var keysList = new ArrayList<>(List.of("text", "autoconfig", FoolsBarrelCommon.MOD_ID));
		keysList.addAll(Arrays.stream(keys).toList());
		this.add(String.join(".", keysList), name);
	}

	private void addConfigTitle(String name) {
		this.addConfig(name, "title");
	}

	private void addConfigCategory(String key, String name) {
		this.addConfig(name, "category", key);
	}

	private void addConfigOption(String name, String...keys) {
		final var keysList = new ArrayList<>(List.of("option"));
		keysList.addAll(Arrays.stream(keys).toList());
		this.addConfig(name, keysList.toArray(new String[0]));
	}

	private void addCommonConfigOption(String key, String name) {
		this.addConfigOption(name, "common", key);
	}

	private void addClientConfigOption(String key, String name) {
		this.addConfigOption(name, "client", key);
	}
}
