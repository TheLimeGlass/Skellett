package com.gmail.thelimeglass.Utils.Packets;

import java.lang.reflect.InvocationTargetException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.Events.EvtPacket;
import com.gmail.thelimeglass.Utils.ReflectionUtil;

import io.netty.channel.*;

public class PacketListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		if (Skellett.syntaxToggleData.getBoolean("Main.Packets")) {
			create(event.getPlayer());
		}
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		remove(event.getPlayer());
	}
	
	private void remove(Player player) {
		try {
			Channel channel = ReflectionUtil.getChannel(player);
			channel.eventLoop().submit(() -> {
				channel.pipeline().remove(player.getName());
				return null;
			});
		} catch (SecurityException | NoSuchMethodException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	private void create(Player player) {
		ChannelDuplexHandler channelDuplexHandler = new ChannelDuplexHandler() {
			@Override
			public void channelRead(ChannelHandlerContext context, Object packet) throws Exception {
				EvtPacket EvtPacket = new EvtPacket(player, new PacketWrapper(packet));
				Bukkit.getServer().getPluginManager().callEvent(EvtPacket);
				if (EvtPacket.isCancelled()) {
					return;
				}
				super.channelRead(context, packet);
			}

			@Override
			public void write(ChannelHandlerContext context, Object packet, ChannelPromise channelPromise) throws Exception {
				EvtPacket EvtPacket = new EvtPacket(player, new PacketWrapper(packet));
				Bukkit.getServer().getPluginManager().callEvent(EvtPacket);
				if (EvtPacket.isCancelled()) {
					return;
				}
				super.write(context, packet, channelPromise);
			}
		};
		try {
			Channel channel = ReflectionUtil.getChannel(player);
			ChannelPipeline pipeline = channel.pipeline();
			pipeline.addBefore("packet_handler", player.getName(), channelDuplexHandler);
		} catch (SecurityException | NoSuchMethodException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}