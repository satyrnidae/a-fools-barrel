package dev.satyrn.foolsbarrel.network;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public final class BarrelInventoryTracker {
	private static final ConcurrentHashMap<UUID, UUID> VIEWER_TO_TARGET = new ConcurrentHashMap<>();
	private static final ConcurrentHashMap<UUID, Set<UUID>> TARGET_TO_VIEWERS = new ConcurrentHashMap<>();

	private BarrelInventoryTracker() {
	}

	public static void track(final UUID viewer, final UUID target) {
		VIEWER_TO_TARGET.put(viewer, target);
		TARGET_TO_VIEWERS.computeIfAbsent(target, k -> ConcurrentHashMap.newKeySet()).add(viewer);
	}

	/**
	 * Removes the viewer. Returns the target UUID only if this was the last viewer
	 * (i.e. the barrel should now close); otherwise returns null.
	 */
	public static @Nullable UUID untrack(final UUID viewer) {
		final UUID target = VIEWER_TO_TARGET.remove(viewer);
		if (target == null) return null;
		final Set<UUID> viewers = TARGET_TO_VIEWERS.get(target);
		if (viewers != null) {
			viewers.remove(viewer);
			if (viewers.isEmpty()) {
				TARGET_TO_VIEWERS.remove(target);
				return target;
			}
		}
		return null;
	}
}
