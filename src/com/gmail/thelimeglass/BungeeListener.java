package com.gmail.thelimeglass;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class BungeeListener implements Runnable {

	private Socket socket = null;

	public BungeeListener(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		try {
			ObjectInputStream objIN = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream objOUT = new ObjectOutputStream(socket.getOutputStream());
			Object obj = objIN.readObject();
			BungeeEventPacket packet = (BungeeEventPacket) obj;
			Object packetData = BungeeEventHandler.handleEvent(packet, socket.getInetAddress());
			if (packetData != null) {
				objOUT.writeObject(packetData);
			}
			objIN.close();
			objOUT.close();
		} catch(IOException | ClassNotFoundException e) {}
		try {
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}