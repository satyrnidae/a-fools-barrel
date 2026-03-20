package dev.satyrn.foolsbarrel.data.tags;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.lepidoptera.util.NotInitializable;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
	public static final TagKey<Item> BARRELS = register("barrels");

	private ModItemTags() {
		NotInitializable.staticClass(ModItemTags.class);
	}

	public static TagKey<Item> register(String name) {
		return TagKey.create(Registry.ITEM_REGISTRY, FoolsBarrelCommon.createId(name));
	}
}
