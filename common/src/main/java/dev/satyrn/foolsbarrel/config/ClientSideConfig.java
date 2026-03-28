package dev.satyrn.foolsbarrel.config;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.foolsbarrel.config.partitions.ClientPartition;
import dev.satyrn.foolsbarrel.config.partitions.CommonPartition;
import dev.satyrn.lepidoptera.api.config.serializers.YamlComment;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import javax.annotation.Nullable;
import java.beans.BeanProperty;
import java.beans.JavaBean;

@JavaBean
@Config(name = FoolsBarrelCommon.MOD_ID)
@YamlComment("""
	Combined client-sided configuration file
	If you're seeing this file, something went weird and wrong.
	It might still work though. The serializer was swapped somehow.
	Mod version 119.2.3""")
public class ClientSideConfig extends PartitioningSerializer.GlobalData {

	@Environment(EnvType.CLIENT) @ConfigEntry.Category("client") @ConfigEntry.Gui.TransitiveObject
	private final ClientPartition client = new ClientPartition();

	@ConfigEntry.Category("common") @ConfigEntry.Gui.TransitiveObject
	private final CommonPartition common = new CommonPartition();

	public ClientSideConfig() {
	}

	@BeanProperty
	@Environment(EnvType.CLIENT)
	@YamlComment(value = "The client specific configuration settings.", sectionHeader = true)
	public ClientPartition getClient() {
		return client;
	}

	@SuppressWarnings("unused")
	@Environment(EnvType.CLIENT)
	public void setClient(final @Nullable ClientPartition value) {
		this.client.copyFrom(value == null ? new ClientPartition() : value);
	}

	@BeanProperty
	@YamlComment(value = "The configuration settings that are shared between client and server.", sectionHeader = true)
	public CommonPartition getCommon() {
		return this.common;
	}

	@SuppressWarnings("unused")
	public void setCommon(final @Nullable CommonPartition value) {
		this.common.copyFrom(value == null ? new CommonPartition() : value);
	}

}

