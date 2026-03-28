package dev.satyrn.foolsbarrel.neoforge.data.server;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.foolsbarrel.data.tags.ModItemTags;
import dev.satyrn.lepidoptera.api.item.ApiItemTags;
import dev.satyrn.lepidoptera.neoforge.api.provider.server.tags.item.ModOnlyItemTagsProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.Tags;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.concurrent.CompletableFuture;

public class ModItemTagsProvider extends ModOnlyItemTagsProvider {
	public ModItemTagsProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, ExistingFileHelper existingFileHelper) {
		super(FoolsBarrelCommon.class, output, lookupProvider, existingFileHelper);
	}

	@Override
	protected void addModTags(HolderLookup.Provider arg) {
		this.tag(ModItemTags.BARRELS)
			.add(Items.BARREL)
			.addOptional(Tags.Items.BARRELS.location())
			.addOptional(Tags.Items.BARRELS_WOODEN.location());

		this.tag(ApiItemTags.HEAD_EQUIPMENT)
			.addTag(ModItemTags.BARRELS);
	}
}
