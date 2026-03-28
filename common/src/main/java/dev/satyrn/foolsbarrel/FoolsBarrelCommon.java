package dev.satyrn.foolsbarrel;

import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import dev.satyrn.foolsbarrel.api.config.ClientConfig;
import dev.satyrn.foolsbarrel.api.config.CommonConfig;
import dev.satyrn.foolsbarrel.api.config.ServerConfig;
import dev.satyrn.foolsbarrel.config.ClientSideConfig;
import dev.satyrn.foolsbarrel.config.ServerSideConfig;
import dev.satyrn.foolsbarrel.config.codecs.ClientPartitionCodec;
import dev.satyrn.foolsbarrel.config.codecs.CommonPartitionCodec;
import dev.satyrn.foolsbarrel.config.partitions.ClientPartition;
import dev.satyrn.foolsbarrel.config.partitions.CommonPartition;
import dev.satyrn.foolsbarrel.sounds.FoolsBarrelSoundEvents;
import dev.satyrn.lepidoptera.api.ModMeta;
import dev.satyrn.lepidoptera.api.config.sync.ConfigOverlay;
import dev.satyrn.lepidoptera.api.config.sync.ServerConfigSync;
import dev.satyrn.lepidoptera.api.config.sync.SyncedConfig;
import dev.satyrn.lepidoptera.api.lang.T9n;
import dev.satyrn.lepidoptera.api.config.serializers.CommentedYamlConfigSerializer;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

@ModMeta(value = "foolsbarrel", name = "A Fool's Barrel", semVer = "21.1.0")
public final class FoolsBarrelCommon {
    public static final String MOD_ID = "foolsbarrel";
	public static final int NETWORK_VERSION = 1;
	public static final String I18N_VERSION_MISMATCH = T9n.netMsg(MOD_ID, "versionMismatch");

	private static final Logger LOGGER = LogManager.getLogger();

	public static ServerConfigSync CONFIG_SYNC;
	public static SyncedConfig<CommonPartition> SYNCED_COMMON;
	public static SyncedConfig<ClientPartition> SYNCED_CLIENT;

	private FoolsBarrelCommon() {
	}

    public static void init() {
		log(Level.INFO, "INIT: Registering sound events for A Fool's Barrel");
		FoolsBarrelSoundEvents.register();

		if (Platform.getEnvironment() == Env.CLIENT) {
			AutoConfig.register(ClientSideConfig.class, PartitioningSerializer.wrap(CommentedYamlConfigSerializer::new));

			final var localCommon = AutoConfig.getConfigHolder(ClientSideConfig.class).getConfig().getCommon();
			final var localClient = AutoConfig.getConfigHolder(ClientSideConfig.class).getConfig().getClient();
			final var builder = ServerConfigSync.builder(MOD_ID)
					.networkVersion(NETWORK_VERSION, I18N_VERSION_MISMATCH);
			SYNCED_COMMON = builder.commonConfig(CommonPartitionCodec.INSTANCE, localCommon);
			SYNCED_CLIENT = builder.clientOverride(() -> false, ClientPartitionCodec.INSTANCE, localClient);
			CONFIG_SYNC = builder.register();
		} else {
			AutoConfig.register(ServerSideConfig.class, PartitioningSerializer.wrap(CommentedYamlConfigSerializer::new));

			final var server = AutoConfig.getConfigHolder(ServerSideConfig.class).getConfig().getServer();
			final var builder = ServerConfigSync.builder(MOD_ID)
					.networkVersion(NETWORK_VERSION, I18N_VERSION_MISMATCH);
			builder.commonConfig(CommonPartitionCodec.INSTANCE, () -> {
					final CommonPartition common = new CommonPartition();
					common.setAllowJumping(server.getAllowJumping());
					common.setShouldAnimalsIgnoreHidingPlayers(server.getShouldAnimalsIgnoreHidingPlayers());
					common.setShouldBarrelHideSightline(server.getShouldBarrelHideSightline());
					common.setShouldHidingRemoveMobAggro(server.getShouldHidingRemoveMobAggro());
					common.setSnapHidingPlayersToGrid(server.getSnapHidingPlayersToGrid());
					return common;
				}, new ConfigOverlay<>());
			builder.clientOverride(server::getShouldOverrideClientConfig, ClientPartitionCodec.INSTANCE, server.getClientOverrides());
			CONFIG_SYNC = builder.register();
		}
    }

	public static void postInit() {
		log(Level.INFO, "POST_INIT: Registering dispenser behavior for A Fool's Barrel");
		DispenserBlock.registerBehavior(Items.BARREL, new OptionalDispenseItemBehavior() {
			@Override
			protected ItemStack execute(BlockSource blockSource, ItemStack itemStack) {
				this.setSuccess(ArmorItem.dispenseArmor(blockSource, itemStack));
				return itemStack;
			}
		});
	}

	public static void info(String msg, Object... fmt) {
		log(Level.INFO, msg, fmt);
	}

	public static void log(Level level, String msg, Object... fmt) {
		LOGGER.log(level, msg, fmt);
	}

	@Environment(EnvType.CLIENT)
	public static ClientConfig<?> getClientConfig() {
		return SYNCED_CLIENT.get();
	}

	public static CommonConfig<?> getCommonConfig() {
		if (Platform.getEnvironment() == Env.CLIENT) {
			return SYNCED_COMMON.get();
		}
		return AutoConfig.getConfigHolder(ServerSideConfig.class).getConfig().getServer();
	}

	@Environment(EnvType.SERVER)
	@SuppressWarnings("unused")
	public static ServerConfig<?> getServerConfig() {
		return AutoConfig.getConfigHolder(ServerSideConfig.class).getConfig().getServer();
	}

	public static ResourceLocation createId(String path) {
		info("Registering new resource location {}:{}", MOD_ID, path);
		final @Nullable ResourceLocation resourceLocation = ResourceLocation.tryBuild(MOD_ID, path);
		if (resourceLocation == null) {
			throw new IllegalStateException("Failed to create key " + MOD_ID + ":" + path);
		}
		return resourceLocation;
	}
}
