package com.gmail.thelimeglass;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import org.bukkit.Bukkit;

public class Sockets {
	
	private static Socket client;
	
	public static void connect() {
		try {
			client = new Socket(Skellett.spData.getString("Host"), Skellett.spData.getInt("Port"));
			ObjectOutputStream objOUT = new ObjectOutputStream(client.getOutputStream());
			ObjectInputStream objIN = new ObjectInputStream(client.getInputStream());
			//ArrayList<Object> data = new ArrayList<Object>(Arrays.asList(Bukkit.getPort(), Bukkit.getServerName(), Bukkit.getMotd(), Bukkit.,Bukkit.getMaxPlayers()));
			SkellettPacket packet = new SkellettPacket(true, Bukkit.getServerName() + ":" + Bukkit.getPort(), SkellettPacketType.PING);
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
	}
	public static Object send(SkellettPacket packet) {
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
			e.printStackTrace();
		}
		return answer;
	}
}