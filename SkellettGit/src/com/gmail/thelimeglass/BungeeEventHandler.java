package com.gmail.thelimeglass;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import com.gmail.thelimeglass.SkellettProxy.Events.EvtBungeecordConnect;
import com.gmail.thelimeglass.SkellettProxy.Events.EvtBungeecordDisconnect;
import com.gmail.thelimeglass.Utils.Utils;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;

public class BungeeEventHandler {
	
	public static Object handleEvent(BungeeEventPacket packet, InetAddress address) {
		switch (packet.getType()) {
		case PINGSERVER:
			break;
		case PLAYERCHAT:
			/*@SuppressWarnings("unchecked")
			ArrayList<Object> data = (ArrayList<Object>) packet.getObject();
			String msg = (String) data.get(1);
			Player reciever = null;
			if (data.get(2) != null) {
				reciever = Bukkit.getPlayer((UUID) data.get(2));
			}
			EvtBungeecordPlayerChat chatEvent = new EvtBungeecordPlayerChat(player, msg, reciever);
			Bukkit.getPluginManager().callEvent(chatEvent);
			if (chatEvent.isCancelled()) {
				return true;
			}*/
			break;
		case PLAYERDISCONNECT:
			OfflinePlayer playerDisconnect = Bukkit.getOfflinePlayer((UUID) packet.getObject());
			if (playerDisconnect != null) {
				Bukkit.getPluginManager().callEvent(new EvtBungeecordDisconnect(playerDisconnect));
			}
			break;
		case PLAYERLOGIN:
			OfflinePlayer playerLogin = Bukkit.getOfflinePlayer((UUID) packet.getObject());
			if (playerLogin != null) {
				Bukkit.getPluginManager().callEvent(new EvtBungeecordConnect(playerLogin));
			}
			break;
		case EVALUATE:
			String evaluate = (String) packet.getObject();
			Effect e = Effect.parse(evaluate, null);
			e.run(null);
			break;
		case GLOBALSCRIPTS:
			@SuppressWarnings("unchecked")
			HashMap<String, List<String>> data = (HashMap<String, List<String>>) packet.getObject();
			FilenameFilter enabledFilter = new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".sk") && !name.startsWith("-");
				}
			};
			FilenameFilter allFilter = new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".sk") && !name.startsWith("-");
				}
			};
			try {
				File scriptsFolder = new File(Skript.getInstance().getDataFolder().getAbsolutePath() + File.separator + Skript.SCRIPTSFOLDER);
				File[] scriptsArray = Utils.getFiles(scriptsFolder, enabledFilter);
				ArrayList<String> scripts = new ArrayList<String>();
				for (File s : Utils.getFiles(scriptsFolder, allFilter)) {
					scripts.add(s.getName());
				}
				scriptsLoop: for (File script : scriptsArray) {
					for (String name : data.keySet()) {
						List<String> lines = data.get(name);
						File tempFile = File.createTempFile("Skellett", name);
						PrintStream out = new PrintStream(new FileOutputStream(tempFile));
						out.print(StringUtils.join(lines, '\n'));
						out.close();
						if (script.getName().equals(name)) {
							if (FileUtils.contentEquals(script, tempFile)) {
								continue scriptsLoop;
							} else {
								try {
									Method method = ScriptLoader.class.getDeclaredMethod("unloadScript", File.class);
									method.setAccessible(true);
									method.invoke(null, script);
								} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
									e1.printStackTrace();
								}
								FileUtils.deleteQuietly(script);
								FileUtils.moveFile(tempFile, script);
								ScriptLoader.loadStructures(new File[] {script});
								ScriptLoader.loadScripts(new File[] {script});
								if (Skellett.spData.getBoolean("GlobalScriptReloadMessage")) {
									Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&6GlobalScript " + name + " has been reloaded!"));
								}
								continue scriptsLoop;
							}
						} else if (!scripts.contains(name)) {
							File newLoc = new File(scriptsFolder + File.separator + name);
							if (!newLoc.exists()) {
								FileUtils.moveFile(tempFile, newLoc);
							}
							ScriptLoader.loadStructures(new File[] {newLoc});
							ScriptLoader.loadScripts(new File[] {newLoc});
							if (Skellett.spData.getBoolean("GlobalScriptReloadMessage")) {
								Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&6GlobalScripts: created " + name + " for this server!"));
							}
							scripts.add(name);
							continue scriptsLoop;
						}
					}
				}
			} catch (IOException error) {
				error.printStackTrace();
			}
			break;
		}
		return null;
	}
}
	