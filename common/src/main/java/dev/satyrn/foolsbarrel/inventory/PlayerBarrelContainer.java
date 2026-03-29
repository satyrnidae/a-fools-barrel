package dev.satyrn.foolsbarrel.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

/**
 * Wraps slots 9–35 of a player's inventory as a {@link Container}, exposing the 27 main
 * inventory slots (excluding hotbar, armor, and offhand) as a 3×9 chest-sized container.
 * All operations delegate live to the underlying {@link Inventory} — no item copying.
 */
public class PlayerBarrelContainer implements Container {
	private final Inventory playerInventory;

	public PlayerBarrelContainer(final Player player) {
		this.playerInventory = player.getInventory();
	}

	@Override
	public int getContainerSize() {
		return 27;
	}

	@Override
	public boolean isEmpty() {
		for (int i = 9; i < 36; i++) {
			if (!this.playerInventory.getItem(i).isEmpty()) return false;
		}
		return true;
	}

	@Override
	public ItemStack getItem(final int slot) {
		return this.playerInventory.getItem(slot + 9);
	}

	@Override
	public ItemStack removeItem(final int slot, final int amount) {
		return this.playerInventory.removeItem(slot + 9, amount);
	}

	@Override
	public ItemStack removeItemNoUpdate(final int slot) {
		return this.playerInventory.removeItemNoUpdate(slot + 9);
	}

	@Override
	public void setItem(final int slot, final ItemStack stack) {
		this.playerInventory.setItem(slot + 9, stack);
	}

	@Override
	public int getMaxStackSize() {
		return this.playerInventory.getMaxStackSize();
	}

	@Override
	public void setChanged() {
		this.playerInventory.setChanged();
	}

	@Override
	public boolean stillValid(final Player player) {
		return true;
	}

	@Override
	public void clearContent() {
		for (int i = 9; i < 36; i++) {
			this.playerInventory.setItem(i, ItemStack.EMPTY);
		}
	}
}
