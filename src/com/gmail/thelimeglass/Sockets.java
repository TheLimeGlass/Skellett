package com.gmail.thelimeglass;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Arrays;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import ch.njol.skript.Skript;

public class Sockets {
	
	private static Socket client;
	private static Integer task, heartbeat;
	private static ServerSocket server;
	
	public static void run() {
		heartbeat = Bukkit.getScheduler().scheduleSyncDelayedTask(Skellett.instance, new Runnable() {
			@Override
			public void run() {
				connect();
				heartbeat();
				if (Skellett.spData.getBoolean("Events")) {
					Bukkit.getScheduler().runTaskAsynchronously(Skellett.plugin, new Runnable() {
						@Override
						public void run() {
							try {
								server = new ServerSocket(Skellett.spData.getInt("EventPort", 7331));
								Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "SkellettProxy connection established on port " + Skellett.spData.getInt("EventPort", 7331)));
								while(!server.isClosed()) {
									try {
										Socket socket = server.accept();
										new Thread(new BungeeListener(socket)).start();
									} catch(SocketException e) {
										Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cBungeecord went offline. Attempting to reconnect..."));
									} catch (IOException e) {
										e.printStackTrace();
									}
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					});
				}
			}
		}, 1);
	}
	
	public static void connect() {
		if (isOpen()) {
			try {
				client = new Socket(Skellett.spData.getString("Host"), Skellett.spData.getInt("Port"));
				ObjectOutputStream objOUT = new ObjectOutputStream(client.getOutputStream());
				ObjectInputStream objIN = new ObjectInputStream(client.getInputStream());
				ArrayList<String> whitelisted = new ArrayList<String>();
				for (OfflinePlayer p : Bukkit.getWhitelistedPlayers()) {
					whitelisted.add(p.getName());
				}
				ArrayList<Object> data = new ArrayList<Object>(Arrays.asList(Skellett.spData.getBoolean("Events", false), Skellett.spData.getInt("EventPort", 7331), Bukkit.getPort(), whitelisted, Skellett.spData.getInt("Heartbeat", 50) * 60, Bukkit.getMotd(), Bukkit.getMaxPlayers()));
				SkellettPacket packet = new SkellettPacket(true, data, SkellettPacketType.PING);
				objOUT.writeObject(packet);
				Object answer = objIN.readObject();
				if (answer != null) {
					Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "Connected to SkellettProxy!"));
				}
				objOUT.close();
				objIN.close();
				client.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		} else {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cIncorrect SkellettProxy details, No socket found or was denied access. For socket at " + Skellett.spData.getString("Host") + ":" + Skellett.spData.getInt("Port")));
		}
	}
	public static Object send(SkellettPacket packet) {
		if (!isOpen()) {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cCould not send packet " + packet.getType() + " to socket at " + Skellett.spData.getString("Host") + ":" + Skellett.spData.getInt("Port") + ". Due to closed socket on other side."));
			return null;
		}
		Object answer = null;
		try {
			client = new Socket(Skellett.spData.getString("Host"), Skellett.spData.getInt("Port"));
			ObjectOutputStream objOUT = new ObjectOutputStream(client.getOutputStream());
			ObjectInputStream objIN = new ObjectInputStream(client.getInputStream());
			objOUT.writeObject((SkellettPacket) packet);
			if (packet.isReturnable()) {
				answer = objIN.readObject();
			}
			objOUT.close();
			objIN.close();
			client.close();
		} catch (IOException | ClassNotFoundException e) {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cIncorrect SkellettProxy details, No socket found or was denied access. For socket at " + Skellett.spData.getString("Host") + ":" + Skellett.spData.getInt("Port")));
			Skript.error("If you're 100% sure your details are correct, this can be caused because some server's want a number host rather than localhost. Try inserting 0.0.0.0 or 127.0.0.1 instead of localhost. Error: ");
			//e.printStackTrace();
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cAttempting to reconnect and fix myself..."));
			stop(true);
		}
		return answer;
	}
	@SuppressWarnings("deprecation")
	public static void heartbeat() {
		task = Bukkit.getScheduler().scheduleAsyncRepeatingTask(Skellett.plugin, new Runnable() {
			@Override
			public void run() {
				if (!isOpen()) {
					if (Skellett.spData.getBoolean("Disconnect")) {
						stop(false);
					}
				} else {
					SkellettPacket packet = new SkellettPacket(true, Bukkit.getPort(), SkellettPacketType.HEARTBEAT);
					Boolean restart = (Boolean) send(packet);
					if (restart == null) stop(true);
					else if (restart) {
						stop(true);
					}
				}
			}
		}, 1, Skellett.spData.getInt("Heartbeat", 50));
	}
	
	public static void stop(Boolean restart) {
		Bukkit.getScheduler().cancelTask(task);
		Bukkit.getScheduler().cancelTask(heartbeat);
		try {
			client.close();
			server.close();
		} catch (IOException e) {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cError closing sockets: "));
			e.printStackTrace();
			return;
		}
		if (restart) {
			run();
		} else {
			Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cDisconnected from SkellettProxy!"));
		}
	}
	
	public static Boolean isOpen() {
		Boolean result = false;
		try {
			Socket attempt = new Socket();
			attempt.connect(new InetSocketAddress(Skellett.spData.getString("Host"), Skellett.spData.getInt("Port")));
			attempt.close();
			result = true;
		} catch (Exception e) {}
		return result;
	}
}