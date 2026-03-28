package dev.satyrn.foolsbarrel.neoforge.data.client;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.foolsbarrel.api.config.BarrelOverlayMethod;
import dev.satyrn.foolsbarrel.api.config.NametagOptions;
import dev.satyrn.foolsbarrel.data.tags.ModItemTags;
import dev.satyrn.foolsbarrel.sounds.FoolsBarrelSoundEvents;
import dev.satyrn.lepidoptera.api.lang.T9n;
import dev.satyrn.lepidoptera.neoforge.api.provider.client.lang.ModLanguageProvider;
import net.minecraft.data.PackOutput;

public class ModUSEnglishLanguageProvider extends ModLanguageProvider {
	public ModUSEnglishLanguageProvider(PackOutput output) {
		super(FoolsBarrelCommon.class, output, "en_us");
	}

	@Override
	protected void addTranslations() {
		this.add("subtitles." + FoolsBarrelSoundEvents.BARREL_EQUIP_SOUND.getId().getPath(), "Barrel equipped");
		this.add(T9n.tip(FoolsBarrelCommon.class, "wearable_barrels"), "Barrels can be worn on your head! Hide in them by crouching.");

		this.add(T9n.configTitle(FoolsBarrelCommon.MOD_ID), "A Fool's Barrel - Options");

		this.add(T9n.configKey(FoolsBarrelCommon.MOD_ID, "category", "client"), "Client Options");
		this.add(T9n.configOption(FoolsBarrelCommon.MOD_ID, "client", "adjustCameraInBarrel"), "Lower camera when barrel is worn?");
		this.add(T9n.configOption(FoolsBarrelCommon.MOD_ID, "client", "overlayMethod"), "How should the barrel overlay display?");
		this.add(T9n.configOption(FoolsBarrelCommon.MOD_ID, "client", "hideNametag"), "Hide player nametag while barrel is worn:");
		this.add(NametagOptions.foolsbarrel$nametag$always, "Always");
		this.add(NametagOptions.foolsbarrel$nametag$when_crouched, "When crouched");
		this.add(NametagOptions.foolsbarrel$nametag$never, "Never");
		this.add(BarrelOverlayMethod.foolsbarrel$overlay$disabled, "Disabled");
		this.add(BarrelOverlayMethod.foolsbarrel$overlay$pin_vertically, "Pin vertically");
		this.add(BarrelOverlayMethod.foolsbarrel$overlay$move_with_look, "Move with look");

		this.add(T9n.configKey(FoolsBarrelCommon.MOD_ID, "category", "common"), "Common Options");
		this.add(T9n.configOption(FoolsBarrelCommon.MOD_ID, "common", "snapHidingPlayersToGrid"), "Snap players to voxel grid when they hide?");
		this.add(T9n.configOption(FoolsBarrelCommon.MOD_ID, "common", "allowJumping"), "Allow players in barrels to jump?");
		this.add(T9n.configOption(FoolsBarrelCommon.MOD_ID, "common", "shouldBarrelHideSightline"), "Should a barrel obscure sightline from Endermen?");
		this.add(T9n.configOption(FoolsBarrelCommon.MOD_ID, "common", "shouldAnimalsIgnoreHidingPlayers"), "Should animals not notice hidden players?");
		this.add(T9n.configOption(FoolsBarrelCommon.MOD_ID, "common", "shouldHidingRemoveMobAggro"), "Should hiding stop mobs from continuing an attack?");
		this.add(T9n.configOption(FoolsBarrelCommon.MOD_ID, "common", "shouldAllowHidingPlayerInventory"), "Should other players be able to open hiding players' inventories?");

		this.add(T9n.netMsg(FoolsBarrelCommon.MOD_ID, "versionMismatch"), "Fools Barrel network version mismatch! Server version is {} while client version is {}.");

		this.add(T9n.itemTag(ModItemTags.BARRELS), "Barrels");
	}
}
