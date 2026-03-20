package dev.satyrn.foolsbarrel.config;

import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import dev.satyrn.foolsbarrel.config.partitions.ServerPartition;
import dev.satyrn.lepidoptera.annotations.YamlComment;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import javax.annotation.Nullable;
import java.beans.BeanProperty;
import java.beans.JavaBean;

@JavaBean
@Config(name = FoolsBarrelCommon.MOD_ID)
public class ServerSideConfig extends PartitioningSerializer.GlobalData {

	@ConfigEntry.Category("server") @ConfigEntry.Gui.TransitiveObject
	private final ServerPartition server = new ServerPartition();

	public ServerSideConfig() {
	}

	@BeanProperty
	public ServerPartition getServer() {
		return this.server;
	}

	@SuppressWarnings("unused")
	public void setServer(final @Nullable ServerPartition value) {
		this.server.copyFrom(value == null ? new ServerPartition() : value);
	}

}

