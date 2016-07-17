package com.gmail.thelimeglass.SkellettAPI;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;


public class SkellettNpc {
	public static HashMap<String, SkellettNpc> NpcData;
	private String name;
	private Location loc;
	private Class<?> EntityPlayerClass;
	private Class<?> PacketPlayOutPlayerInfo;
	private Class<?> EnumPlayerInfoAction;
	private Constructor<?> EntityPlayerConstructor;
	private Object EntityPlayer;
	private Object MinecraftServer;
	private int id;
	private Object WorldServer;
	private Object World;
	private Object PlayerInteractManager;
	private Object SpawnPacket;
	private Object TabPacket;
	private Method setLocation;
	private Object ADD_PLAYER;
	private Object packetOutEntity;
	private Object packetHead;
	private UUID uuid;
	private String skin;
 
	public String getSkin() {
		return this.skin;
	}
 
	private GameProfile GameProfile;
 
	public UUID getUUID() {
		return uuid;
	}
 
	public Location getLocation() {
		return this.loc;
	}
 
	public void destroy() {
		try {
			removeEntityFromWorld(loc.getWorld());
			Object REMOVE_PLAYER = EnumPlayerInfoAction.getField("REMOVE_PLAYER").get(null);
			Object array = getArray(EntityPlayerClass, EntityPlayer);
			Constructor<?> Constructor = PacketPlayOutPlayerInfo.getConstructor(REMOVE_PLAYER.getClass(), array.getClass());
			TabPacket = Constructor.newInstance(REMOVE_PLAYER, getArray(EntityPlayerClass, EntityPlayer));
			SpawnPacket = getNMSClass("PacketPlayOutEntityDestroy").getConstructor(int[].class).newInstance(getArray(int.class, id));
			for(Player p : Bukkit.getOnlinePlayers()) {
				sendPackets(p, SpawnPacket, TabPacket);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
 
	private byte getFixRotation(float yawpitch) {
		return (byte) ((int)(yawpitch * 256.0F / 360.0F));
	}
 
	private Object getArray(Class<?> type, Object... objects) {
		Object array = Array.newInstance(type, objects.length);  
		try {
			Integer contador = 0;
			for(Object o : objects) {
				Array.set(array, contador, o);
				contador++;
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return array;
	}
 
	public void setCustomName(String name) {
		try {
			destroy();
			GameProfile = new GameProfile(uuid, name);
			EntityPlayer = EntityPlayerConstructor.newInstance(MinecraftServer, WorldServer, GameProfile, PlayerInteractManager);  
			spawn();
			id = (Integer) EntityPlayer.getClass().getMethod("getId").invoke(EntityPlayer);
			setGameMode(GameMode.SURVIVAL);
			setGameMode(GameMode.CREATIVE);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
 
	public void setSkin(String name) {
		try {
			destroy();
			GameProfile = new ProfileLoader(uuid.toString(), this.name, name).loadProfile();
			EntityPlayer = EntityPlayerConstructor.newInstance(MinecraftServer, WorldServer, GameProfile, PlayerInteractManager);  
			spawn();
			id = (Integer) EntityPlayer.getClass().getMethod("getId").invoke(EntityPlayer);
			setGameMode(GameMode.SURVIVAL);
			setGameMode(GameMode.CREATIVE);
			this.skin = name;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
 
	public String getName() {
		return this.name;
	}
 
	public void spawn() {
		//You can't spawn the NPC if you haven't destroyed it before
		try {
			Object array = getArray(EntityPlayerClass, EntityPlayer);
			Constructor<?> Constructor = PacketPlayOutPlayerInfo.getConstructor(ADD_PLAYER.getClass(), array.getClass());
			TabPacket = Constructor.newInstance(ADD_PLAYER, getArray(EntityPlayerClass, EntityPlayer));
			SpawnPacket = getNMSClass("PacketPlayOutNamedEntitySpawn").getConstructor(EntityPlayer.getClass().getSuperclass()).newInstance(EntityPlayer);
			setLocation.invoke(EntityPlayer, loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
			packetOutEntity = getNMSClass("PacketPlayOutEntity").getClasses()[0].getDeclaredConstructors()[1].newInstance(id, getFixRotation(loc.getYaw()), getFixRotation(loc.getPitch()), true);
			Constructor = getNMSClass("PacketPlayOutEntityHeadRotation").getConstructor(EntityPlayerClass.getSuperclass().getSuperclass().getSuperclass(), byte.class);
			packetHead = Constructor.newInstance(EntityPlayer, getFixRotation(loc.getYaw()));
			for(Player p : Bukkit.getOnlinePlayers()) {
				sendPackets(p, SpawnPacket, TabPacket);
			}
			addPlayerConnection();
			addEntityToWorld(loc.getWorld());
			for(Player p : Bukkit.getOnlinePlayers()) {
				sendPackets(p, packetOutEntity, packetHead);
			}
			setGameMode(GameMode.SURVIVAL);
			setGameMode(GameMode.CREATIVE);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
 
	public Object getGameMode() {
		try {
			Object CraftPlayer = EntityPlayerClass.getMethod("getBukkitEntity").invoke(EntityPlayer);
			Method getGameMode = CraftPlayer.getClass().getMethod("getGameMode");
			Object GameMode = getGameMode.invoke(CraftPlayer);
			return GameMode;
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
 
	public void setPassenger(Entity entity) {
		try {
			Object CraftPlayer = EntityPlayerClass.getMethod("getBukkitEntity").invoke(EntityPlayer);
			Method setPassenger = CraftPlayer.getClass().getMethod("setPassenger", Entity.class);
			setPassenger.invoke(CraftPlayer, entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
 
	private void removeEntityFromWorld(World world) {
		try {
			Method removeEntity = WorldServer.getClass().getMethod("removeEntity", EntityPlayerClass.getSuperclass().getSuperclass().getSuperclass());
			removeEntity.invoke(WorldServer, EntityPlayer);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
 
	private void addEntityToWorld(World world) {
		try {
			Object nmsWorld = WorldServer;
			Method addEntity = nmsWorld.getClass().getSuperclass().getMethod("addEntity", EntityPlayerClass.getSuperclass().getSuperclass().getSuperclass(), SpawnReason.class);
			addEntity.invoke(nmsWorld, EntityPlayer, SpawnReason.CUSTOM);
			Object NMSWorld = getWorldNMS(loc.getWorld());
			Field getPlayers = NMSWorld.getClass().getField("players");
			getPlayers.setAccessible(true);
			List<?> players = (List<?>) getPlayers.get(NMSWorld);
			players.remove(EntityPlayer);
			getPlayers.set(NMSWorld, players);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
 
	public void setGameMode(GameMode gm) {
		try {
			Object CraftPlayer = EntityPlayerClass.getMethod("getBukkitEntity").invoke(EntityPlayer);
			Method setGameMode = CraftPlayer.getClass().getMethod("setGameMode", gm.getClass());
			setGameMode.invoke(CraftPlayer, gm);  
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
 
	private void addPlayerConnection() {
		try {
			Constructor<?> newPC = getNMSClass("PlayerConnection").getConstructors()[0];
			Object EnumNetworkManager = getNMSClass("EnumProtocolDirection").getField("SERVERBOUND").get(null);
			Object NetworkManager = getNMSClass("NetworkManager").getConstructors()[0].newInstance(EnumNetworkManager);
			newPC.newInstance(MinecraftServer, NetworkManager, EntityPlayer);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
 
	public SkellettNpc(String name, Location loc, String skin) {
		try {
			this.skin = skin;
			this.name = name;
			this.loc = loc;
			EntityPlayerClass = getNMSClass("EntityPlayer");
			World = getWorldNMS(loc.getWorld());
			MinecraftServer = getMinecraftServerNMS();
			WorldServer = getWorldServerNMS();
			uuid = UUID.randomUUID();
			GameProfile = new ProfileLoader(uuid.toString(), this.name, skin).loadProfile();
			PlayerInteractManager = getNMSClass("PlayerInteractManager").getConstructor(World.getClass().getSuperclass()).newInstance(World);
			EntityPlayerConstructor = EntityPlayerClass.getConstructor(MinecraftServer.getClass().getSuperclass(), WorldServer.getClass(), GameProfile.getClass(), PlayerInteractManager.getClass());
			EntityPlayer = EntityPlayerConstructor.newInstance(MinecraftServer, WorldServer, GameProfile, PlayerInteractManager);
			id = (Integer) EntityPlayer.getClass().getMethod("getId").invoke(EntityPlayer);
			setLocation = EntityPlayerClass.getSuperclass().getSuperclass().getSuperclass().getMethod("setLocation", double.class, double.class, double.class, float.class, float.class);
			setLocation.invoke(EntityPlayer, loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
			PacketPlayOutPlayerInfo = getNMSClass("PacketPlayOutPlayerInfo");
			EnumPlayerInfoAction = PacketPlayOutPlayerInfo.getClasses()[1];
			ADD_PLAYER = EnumPlayerInfoAction.getField("ADD_PLAYER").get(null);
			Object array = getArray(EntityPlayerClass, EntityPlayer);
			Constructor<?> Constructor = PacketPlayOutPlayerInfo.getConstructor(ADD_PLAYER.getClass(), array.getClass());
			TabPacket = Constructor.newInstance(ADD_PLAYER, getArray(EntityPlayerClass, EntityPlayer));
			SpawnPacket = getNMSClass("PacketPlayOutNamedEntitySpawn").getConstructor(EntityPlayer.getClass().getSuperclass()).newInstance(EntityPlayer);
			packetOutEntity = getNMSClass("PacketPlayOutEntity").getClasses()[0].getDeclaredConstructors()[1].newInstance(id, getFixRotation(loc.getYaw()), getFixRotation(loc.getPitch()), true);
			Constructor = getNMSClass("PacketPlayOutEntityHeadRotation").getConstructor(EntityPlayerClass.getSuperclass().getSuperclass().getSuperclass(), byte.class);
			packetHead = Constructor.newInstance(EntityPlayer, getFixRotation(loc.getYaw()));
			for(Player p : Bukkit.getOnlinePlayers()) {
				sendPackets(p, TabPacket, SpawnPacket);
			}
			addPlayerConnection();
			addEntityToWorld(loc.getWorld());
			for(Player p : Bukkit.getOnlinePlayers()) {
				sendPackets(p, packetOutEntity, packetHead);
			}
			setGameMode(GameMode.CREATIVE);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
 
	public SkellettNpc(String name, Location loc) {
		this(name, loc, name);
	}
 
	public void show(Player p) {
		sendPackets(p, TabPacket, SpawnPacket);
		sendPackets(p, packetOutEntity, packetHead);
	}
 
	public Integer getEntityID() {
		return id;
	}
 
	private void sendPackets(Player player, Object... object) {
		try {
			Object handle = player.getClass().getMethod("getHandle").invoke(player);
			Object playerConnection = handle.getClass().getField("playerConnection").get(handle);
			Method send = playerConnection.getClass().getMethod("sendPacket", getNMSClass("Packet"));
			for(Object o : object) {
				 send.invoke(playerConnection, o);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
 
	public void setLocation(Location loc) {
		try {
			setLocation.invoke(EntityPlayer, loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());  
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
 
	private Object getWorldNMS(World world) {
		try {
			Method getHandle = world.getClass().getMethod("getHandle");
			Object nmsWorld = getHandle.invoke(world);
			Object World = getNMSClass("World").cast(nmsWorld);
			return World;
		} catch(Exception e ) {
			e.printStackTrace();
		}
		return null;
	}
 
	private Object getWorldServerNMS() {
		try {
			Method getHandle = loc.getWorld().getClass().getMethod("getHandle");
			return getHandle.invoke(loc.getWorld());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
 
	private Object getMinecraftServerNMS() {
		try {
			Method getHandle = Bukkit.getServer().getClass().getMethod("getServer");
			return getHandle.invoke(Bukkit.getServer());
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
 
	public class ProfileLoader {
		private final String uuid;
		private final String name;
		private final String skinOwner;
	 
		public ProfileLoader(String uuid, String name) {
			this(uuid, name, name);
		}
	 
		public ProfileLoader(String uuid, String name, String skinOwner) {
			this.uuid = uuid == null ? null : uuid.replaceAll("-", ""); //We add these later
			String displayName = ChatColor.translateAlternateColorCodes('&', name);
			this.name = ChatColor.stripColor(displayName);
			this.skinOwner = skinOwner;
		}
	 
		public GameProfile loadProfile() {
			UUID id = uuid == null ? parseUUID(getUUID(name)) : parseUUID(uuid);
			GameProfile profile = new GameProfile(id, name);
			addProperties(profile);
			return profile;
		}
	 
		@SuppressWarnings("resource")
		private void addProperties(GameProfile profile) {
			String uuid = getUUID(skinOwner);
			try {
				URL url = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
				URLConnection uc = url.openConnection();
				uc.setUseCaches(false);
				uc.setDefaultUseCaches(false);
				uc.addRequestProperty("User-Agent", "Mozilla/5.0");
				uc.addRequestProperty("Cache-Control", "no-cache, no-store, must-revalidate");
				uc.addRequestProperty("Pragma", "no-cache");

				// Parse it
				String json = new Scanner(uc.getInputStream(), "UTF-8").useDelimiter("\\A").next();
				JSONParser parser = new JSONParser();
				Object obj = parser.parse(json);
				JSONArray properties = (JSONArray) ((JSONObject) obj).get("properties");
				for (int i = 0; i < properties.size(); i++) {
					try {
						JSONObject property = (JSONObject) properties.get(i);
						String name = (String) property.get("name");
						String value = (String) property.get("value");
						String signature = property.containsKey("signature") ? (String) property.get("signature") : null;
						if (signature != null) {
							profile.getProperties().put(name, new Property(name, value, signature));
						} else {
							profile.getProperties().put(name, new Property(value, name));
						}
					} catch (Exception e) {
						Bukkit.getLogger().log(Level.WARNING, "Failed to apply auth property", e);
					}
				}
			} catch (Exception e) {
				//Failed loading skin, have in mind the Mojang API is public and it have restrictions
				//1 request of profile per minute and 600 requests per 10 minutes, may change
			}
		}

		@SuppressWarnings("deprecation")
		private String getUUID(String name) {
			return Bukkit.getOfflinePlayer(name).getUniqueId().toString().replaceAll("-", "");
		}

		private UUID parseUUID(String uuidStr) {
			String[] uuidComponents = new String[] { uuidStr.substring(0, 8),
					uuidStr.substring(8, 12), uuidStr.substring(12, 16),
					uuidStr.substring(16, 20),
					uuidStr.substring(20, uuidStr.length())
			};

			StringBuilder builder = new StringBuilder();
			for (String component : uuidComponents) {
				builder.append(component).append('-');
			}

			builder.setLength(builder.length() - 1);
			return UUID.fromString(builder.toString());
		}
	}
 
	private Class<?> getNMSClass(String nmsClassString){
		String version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3] + ".";
		String name = "net.minecraft.server." + version + nmsClassString;
		Class<?> nmsClass;
		try {
			nmsClass = Class.forName(name);
			return nmsClass;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

}