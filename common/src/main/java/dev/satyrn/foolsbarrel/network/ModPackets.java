package dev.satyrn.foolsbarrel.network;

import dev.architectury.platform.Platform;
import dev.architectury.utils.Env;
import dev.satyrn.foolsbarrel.FoolsBarrelCommon;
import io.netty.buffer.Unpooled;
import lol.bai.badpackets.api.C2SPacketReceiver;
import lol.bai.badpackets.api.PacketSender;
import lol.bai.badpackets.api.S2CPacketReceiver;
import lol.bai.badpackets.api.event.PacketSenderReadyCallback;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ModPackets {
	public static final ResourceLocation VERSION = FoolsBarrelCommon.createId("version");
	public static final ResourceLocation CONFIG_SYNC_REQ = FoolsBarrelCommon.createId("config_sync_req");
	public static final ResourceLocation CONFIG = FoolsBarrelCommon.createId("config");

	public static final String I18N_VERSION_MISMATCH = FoolsBarrelCommon.MOD_ID + ".network.versionMismatch";

	public static void initServer() {
		PacketSenderReadyCallback.registerServer((handler, sender, server) -> {
			var byteBuf = new FriendlyByteBuf(Unpooled.buffer());
			byteBuf.writeVarInt(FoolsBarrelCommon.NETWORK_VERSION);
			sender.send(VERSION, byteBuf);

			if (Platform.getEnvironment() == Env.SERVER) {
				server.execute(() -> sendConfig(sender));
			}
		});

		C2SPacketReceiver.register(VERSION, (server, player, handler, buf, responseSender) -> {
			var clientVersion = buf.readVarInt();

			if (clientVersion != FoolsBarrelCommon.NETWORK_VERSION) {
				handler.disconnect(Component.translatable(I18N_VERSION_MISMATCH, FoolsBarrelCommon.NETWORK_VERSION, clientVersion));
			}
		});

		C2SPacketReceiver.register(CONFIG_SYNC_REQ, (server, player, handler, buf, responseSender) -> {
			server.execute(() -> sendConfig(responseSender));
		});
	}

	@Environment(EnvType.CLIENT)
	public static void initClient() {
		PacketSenderReadyCallback.registerClient((handler, sender, client) -> {
			var byteBuf = new FriendlyByteBuf(Unpooled.buffer());
			byteBuf.writeVarInt(FoolsBarrelCommon.NETWORK_VERSION);
			sender.send(VERSION, byteBuf);
		});

		S2CPacketReceiver.register(VERSION, ((client, handler, buf, responseSender) -> {
			var serverVersion = buf.readVarInt();
			if (serverVersion != FoolsBarrelCommon.NETWORK_VERSION) {
				handler.getConnection().disconnect(Component.translatable(I18N_VERSION_MISMATCH, serverVersion, FoolsBarrelCommon.NETWORK_VERSION));
			}
		}));

		S2CPacketReceiver.register(CONFIG, ((client, handler, buf, responseSender) -> {
			var includesClientOverrides = buf.readBoolean();

			FoolsBarrelCommon.setCommonConfigOverlay(new NetworkCommonConfig(buf));
			if (includesClientOverrides) {
				FoolsBarrelCommon.setClientConfigOverlay(new NetworkClientConfig(buf));
			}
		}));
	}

	public static void sendConfig(PacketSender packetSender) {
		var byteBuffer = new FriendlyByteBuf(Unpooled.buffer());
		final boolean isServerSide = Platform.getEnvironment() == Env.SERVER;

		boolean overrideClientConfig = false;

		if (isServerSide) {
			overrideClientConfig = FoolsBarrelCommon.getServerConfig().getShouldOverrideClientConfig();
		}

		byteBuffer.writeBoolean(overrideClientConfig);
		FoolsBarrelCommon.getCommonConfig().writeToBuffer(byteBuffer);
		if (overrideClientConfig) {
			FoolsBarrelCommon.getServerConfig().getClientOverrides().writeToBuffer(byteBuffer);
		}

		packetSender.send(CONFIG, byteBuffer);
	}
}
