package dev.satyrn.foolsbarrel.forge.events;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.foolsbarrel.data.tags.ModItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.living.EnderManAngerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FoolsBarrelCommon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class EntityEvents {
	private EntityEvents() {
	}

	@SubscribeEvent
	public static void onAngerEnderMan(final EnderManAngerEvent event) {
		if (FoolsBarrelCommon.getCommonConfig().getShouldBarrelHideSightline() &&
			event.getPlayer().getItemBySlot(EquipmentSlot.HEAD).is(ModItemTags.BARRELS)) {
			event.setCanceled(true);
		}
	}
}
