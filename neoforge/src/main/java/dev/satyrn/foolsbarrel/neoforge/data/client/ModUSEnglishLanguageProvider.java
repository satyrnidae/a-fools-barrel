package dev.satyrn.foolsbarrel.neoforge.data.client;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.foolsbarrel.api.config.BarrelOverlayMethod;
import dev.satyrn.foolsbarrel.api.config.NametagOptions;
import dev.satyrn.foolsbarrel.data.tags.ModItemTags;
import dev.satyrn.foolsbarrel.sounds.FoolsBarrelSoundEvents;
import dev.satyrn.lepidoptera.api.ModMeta;
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
		this.addTip("wearable_barrels", "Barrels can be worn on your head! Hide in them by crouching.");

		this.add(T9n.configTitle(FoolsBarrelCommon.MOD_ID), "A Fool's Barrel - Options");

		this.add(T9n.configKey(FoolsBarrelCommon.MOD_ID, "category", "client"), "Client Options");
		this.add(T9n.configOption(FoolsBarrelCommon.MOD_ID, "client", "adjustCameraInBarrel"), "Lower barrel camera height");
		this.add(T9n.configTooltip(FoolsBarrelCommon.MOD_ID, "client", "adjustCameraInBarrel"), "Lowers the player camera to chest height\nwhen a barrel is being worn");
		this.add(T9n.configOption(FoolsBarrelCommon.MOD_ID, "client", "overlayMethod"), "Overlay method");
		this.add(T9n.configTooltip(FoolsBarrelCommon.MOD_ID, "client", "overlayMethod"), "How should the barrel overlay display?");
		this.add(T9n.configOption(FoolsBarrelCommon.MOD_ID, "client", "hideNametag"), "Nametag visibility");
		this.add(T9n.configTooltip(FoolsBarrelCommon.MOD_ID, "client", "hideNametag"), "When should the nametag be hidden\nfor a barrel-wearing player?");
		this.add(NametagOptions.foolsbarrel$nametag$always, "Always");
		this.add(NametagOptions.foolsbarrel$nametag$when_crouched, "When crouched");
		this.add(NametagOptions.foolsbarrel$nametag$never, "Never");
		this.add(BarrelOverlayMethod.foolsbarrel$overlay$disabled, "Disabled");
		this.add(BarrelOverlayMethod.foolsbarrel$overlay$pin_vertically, "Pin vertically");
		this.add(BarrelOverlayMethod.foolsbarrel$overlay$move_with_look, "Move with look");

		this.add(T9n.configKey(FoolsBarrelCommon.MOD_ID, "category", "common"), "Common Options");
		this.add(T9n.configOption(FoolsBarrelCommon.MOD_ID, "common", "snapHidingPlayersToGrid"), "Snap hiding players to grid");
		this.add(T9n.configTooltip(FoolsBarrelCommon.MOD_ID, "common", "snapHidingPlayersToGrid"), "Snaps players to voxel grid\nwhen they hide");
		this.add(T9n.configOption(FoolsBarrelCommon.MOD_ID, "common", "allowJumping"), "Allow jumping");
		this.add(T9n.configTooltip(FoolsBarrelCommon.MOD_ID, "common", "allowJumping"), "Allows players hiding in barrels to jump");
		this.add(T9n.configOption(FoolsBarrelCommon.MOD_ID, "common", "shouldBarrelHideSightline"), "Hide sightlines in barrel");
		this.add(T9n.configTooltip(FoolsBarrelCommon.MOD_ID, "common", "shouldBarrelHideSightline"), "Whether barrels obscure your\nsightline from Endermen");
		this.add(T9n.configOption(FoolsBarrelCommon.MOD_ID, "common", "shouldAnimalsIgnoreHidingPlayers"), "Animals ignore hiding players");
		this.add(T9n.configTooltip(FoolsBarrelCommon.MOD_ID, "common", "shouldAnimalsIgnoreHidingPlayers"), "Whether animals look at\nhiding players");
		this.add(T9n.configOption(FoolsBarrelCommon.MOD_ID, "common", "shouldHidingRemoveMobAggro"), "Mobs forget hiding players");
		this.add(T9n.configTooltip(FoolsBarrelCommon.MOD_ID, "common", "shouldHidingRemoveMobAggro"), "Should hiding stop mobs from\ncontinuing an attack?");
		this.add(T9n.configOption(FoolsBarrelCommon.MOD_ID, "common", "shouldAllowHidingPlayerInventory"), "Access hiding players' inventories");
		this.add(T9n.configTooltip(FoolsBarrelCommon.MOD_ID, "common", "shouldAllowHidingPlayerInventory"), "Whether other players should be\nable to open hiding players'\ninventories");
		this.add(T9n.configOption(FoolsBarrelCommon.MOD_ID, "common", "canSetBarrelDirectionOnHide"), "Set hiding barrel direction from look");
		this.add(T9n.configTooltip(FoolsBarrelCommon.MOD_ID, "common", "canSetBarrelDirectionOnHide"), "If enabled, the barrel lid faces the\ndirection you're facing when you\nhide.");

		this.add(T9n.netMsg(FoolsBarrelCommon.class, "versionMismatch"), "Fools Barrel network version mismatch! Server version is {} while client version is {}.");

		this.add(T9n.itemTag(ModItemTags.BARRELS), "Barrels");
	}
}
