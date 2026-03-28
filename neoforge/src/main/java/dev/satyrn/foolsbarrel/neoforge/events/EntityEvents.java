package dev.satyrn.foolsbarrel.neoforge.events;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.foolsbarrel.data.tags.ModItemTags;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.EnderManAngerEvent;

@EventBusSubscriber(modid = FoolsBarrelCommon.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
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
