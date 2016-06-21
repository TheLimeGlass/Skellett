/*
package com.gmail.thelimeglass;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.collect.Iterables;
import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

public class SkellettCord extends JavaPlugin implements PluginMessageListener {
	@Override
	public void onEnable() {
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "SkellettCord");
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "SkellettCord", this);
		ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Test");
		Player p = Iterables.getFirst(Bukkit.getOnlinePlayers(), null);
		p.sendPluginMessage(this, "SkellettCord", out.toByteArray());
	}
	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if (!channel.equals("SkellettCord")) {
			return;
		}
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String subchannel = in.readUTF();
		if (subchannel.equals("Test")) {
			Bukkit.getConsoleSender().sendMessage(Main.cc("&6Reply!"));
		}
	}
}
*/
