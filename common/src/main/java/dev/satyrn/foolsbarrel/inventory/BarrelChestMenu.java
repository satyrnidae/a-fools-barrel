package dev.satyrn.foolsbarrel.inventory;

import dev.satyrn.foolsbarrel.api.extensions.world.entity.player.PlayerExtensions;
import dev.satyrn.foolsbarrel.network.BarrelInventoryTracker;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.MenuType;

import java.util.UUID;

/**
 * A {@link ChestMenu} that closes the barrel open state when the last viewer leaves.
 */
public class BarrelChestMenu extends ChestMenu {
	private final UUID targetId;

	public BarrelChestMenu(final int containerId, final Inventory playerInventory,
						   final PlayerBarrelContainer container, final UUID targetId) {
		super(MenuType.GENERIC_9x3, containerId, playerInventory, container, 3);
		this.targetId = targetId;
	}

	@Override
	public void removed(final Player player) {
		super.removed(player);
		if (player.level().isClientSide()) return;
		final UUID lastViewerTarget = BarrelInventoryTracker.untrack(player.getUUID());
		if (lastViewerTarget == null) return;
		if (!(player.level() instanceof ServerLevel serverLevel)) return;
		final ServerPlayer target = serverLevel.getServer().getPlayerList().getPlayer(lastViewerTarget);
		if (target != null) {
			((PlayerExtensions) target).setBarrelOpen(false);
			serverLevel.playSound(null, target, SoundEvents.BARREL_CLOSE, SoundSource.BLOCKS, 1, 1);
		}
	}
}
