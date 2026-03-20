package dev.satyrn.foolsbarrel;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.architectury.event.events.client.ClientPlayerEvent;
import dev.architectury.event.events.common.PlayerEvent;
import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import dev.satyrn.foolsbarrel.api.config.ServerConfig;
import dev.satyrn.foolsbarrel.config.ClientSideConfig;
import dev.satyrn.foolsbarrel.data.tags.ModItemTags;
import dev.satyrn.foolsbarrel.network.ModPackets;
import dev.satyrn.foolsbarrel.network.NetworkClientConfig;
import dev.satyrn.foolsbarrel.network.NetworkCommonConfig;
import dev.satyrn.lepidoptera.api.world.item.EquipmentRegistry;
import dev.satyrn.lepidoptera.config.serializers.CommentedYamlConfigSerializer;
import dev.satyrn.foolsbarrel.api.config.ClientConfig;
import dev.satyrn.foolsbarrel.api.config.CommonConfig;
import dev.satyrn.foolsbarrel.config.ServerSideConfig;
import dev.satyrn.foolsbarrel.sounds.FoolsBarrelSoundEvents;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.DispenserBlock;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nullable;

public final class FoolsBarrelCommon {
    public static final String MOD_ID = "foolsbarrel";
	private static final Logger LOGGER = LogManager.getLogger();
	public static final int NETWORK_VERSION = 1;
	private static final Gson GSON = new Gson();

	@Environment(EnvType.CLIENT)
	private static @Nullable NetworkClientConfig CLIENT_CONFIG_OVERLAY;
	private static @Nullable NetworkCommonConfig COMMON_CONFIG_OVERLAY;

	private FoolsBarrelCommon() {
	}

    public static void init() {
		if (Platform.getEnvironment() == Env.CLIENT) {
			AutoConfig.register(ClientSideConfig.class, PartitioningSerializer.wrap(CommentedYamlConfigSerializer::new));
		} else {
			AutoConfig.register(ServerSideConfig.class, PartitioningSerializer.wrap(CommentedYamlConfigSerializer::new));
		}

        // Write common init code here.
		log(Level.INFO, "INIT: Registering sound events for A Fool's Barrel");
		FoolsBarrelSoundEvents.register();

		if (Platform.getEnvironment() == Env.CLIENT) {
			ModPackets.initClient();
		} else {
			ModPackets.initServer();
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
		EquipmentRegistry.registerEquipment(EquipmentSlot.HEAD, ModItemTags.BARRELS);
	}

	public static void info(String msg, Object...fmt) {
		log(Level.INFO, msg, fmt);
	}

	public static void log(Level level, String msg, Object... fmt) {
		LOGGER.log(level, msg, fmt);
	}

	@Environment(EnvType.CLIENT)
	public static ClientConfig<?> getClientConfig() {
		return CLIENT_CONFIG_OVERLAY == null ? AutoConfig.getConfigHolder(ClientSideConfig.class).getConfig().getClient() : CLIENT_CONFIG_OVERLAY;
	}

	public static CommonConfig<?> getCommonConfig() {
		if (Platform.getEnvironment() == Env.CLIENT) {
			return COMMON_CONFIG_OVERLAY == null ? AutoConfig.getConfigHolder(ClientSideConfig.class).getConfig().getCommon() : COMMON_CONFIG_OVERLAY;
		}
		return AutoConfig.getConfigHolder(ServerSideConfig.class).getConfig().getServer();
	}

	@Environment(EnvType.SERVER)
	@SuppressWarnings("unused")
	public static ServerConfig<?> getServerConfig() {
		return AutoConfig.getConfigHolder(ServerSideConfig.class).getConfig().getServer();
	}

	@Environment(EnvType.CLIENT)
	public static void setClientConfigOverlay(final NetworkClientConfig value) {
		if (CLIENT_CONFIG_OVERLAY == null) {
			CLIENT_CONFIG_OVERLAY = value;
		} else {
			CLIENT_CONFIG_OVERLAY.copyFrom(value);
		}
		info("Received new client-side config from the server: {}", GSON.toJson(value));
	}

	@Environment(EnvType.CLIENT)
	public static void clearClientConfigOverlay() {
		CLIENT_CONFIG_OVERLAY = null;
	}

	@Environment(EnvType.CLIENT)
	public static void setCommonConfigOverlay(final NetworkCommonConfig value) {
		if (COMMON_CONFIG_OVERLAY == null) {
			COMMON_CONFIG_OVERLAY = value;
		} else {
			COMMON_CONFIG_OVERLAY.copyFrom(value);
		}
		info("Received new common config from the server: {}", GSON.toJson(value));
	}

	@Environment(EnvType.CLIENT)
	public static void clearCommonConfigOverlay() {
		COMMON_CONFIG_OVERLAY = null;
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
