package dev.satyrn.foolsbarrel.data.tags;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.lepidoptera.api.NotInitializable;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ModItemTags {
	public static final TagKey<Item> BARRELS = register("barrels");

	private ModItemTags() {
		NotInitializable.staticClass(ModItemTags.class);
	}

	public static TagKey<Item> register(String name) {
		return TagKey.create(Registries.ITEM, FoolsBarrelCommon.createId(name));
	}
}
