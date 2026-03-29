package dev.satyrn.foolsbarrel.neoforge.events;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.foolsbarrel.api.extensions.world.entity.player.PlayerExtensions;
import dev.satyrn.foolsbarrel.data.tags.ModItemTags;
import dev.satyrn.foolsbarrel.network.BarrelInventoryTracker;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.EnderManAngerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import javax.annotation.Nullable;
import java.util.UUID;

@EventBusSubscriber(modid = FoolsBarrelCommon.MOD_ID)
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

	@SubscribeEvent
	public static void onPlayerLoggedOut(final PlayerEvent.PlayerLoggedOutEvent event) {
		if (!(event.getEntity() instanceof ServerPlayer viewer)) return;
		final @Nullable UUID targetId = BarrelInventoryTracker.untrack(viewer.getUUID());
		if (targetId == null) return;
		final @Nullable var server = viewer.getServer();
		if (server == null) return;
		final @Nullable ServerPlayer target = server.getPlayerList().getPlayer(targetId);
		if (target != null) {
			((PlayerExtensions) target).setBarrelOpen(false);
		}
	}
}
