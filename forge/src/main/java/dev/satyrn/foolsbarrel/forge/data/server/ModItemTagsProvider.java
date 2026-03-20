package dev.satyrn.foolsbarrel.forge.data.server;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.foolsbarrel.data.tags.ModItemTags;
import dev.satyrn.lepidoptera.forge.data.provider.server.tags.item.ModOnlyItemTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;

import java.util.Objects;

public class ModItemTagsProvider extends ModOnlyItemTagsProvider {
	public ModItemTagsProvider(DataGenerator arg, ExistingFileHelper existingFileHelper) {
		super(FoolsBarrelCommon.class, arg, existingFileHelper);
	}

	@Override
	protected void addModTags() {
		this.tag(ModItemTags.BARRELS)
			.add(Items.BARREL)
			.addOptional(Tags.Items.BARRELS.location())
			.addOptional(Objects.requireNonNull(ResourceLocation.tryBuild("c", "barrels")));
	}
}
