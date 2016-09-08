package com.gmail.thelimeglass.SkellettAPI;

import java.lang.reflect.Field;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_9_R1.CraftServer;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import com.mojang.authlib.GameProfile;

import net.minecraft.server.v1_9_R1.EntityPlayer;
import net.minecraft.server.v1_9_R1.MinecraftServer;
import net.minecraft.server.v1_9_R1.Packet;
import net.minecraft.server.v1_9_R1.PacketPlayOutPlayerInfo;
import net.minecraft.server.v1_9_R1.PlayerConnection;
import net.minecraft.server.v1_9_R1.PlayerInteractManager;
import net.minecraft.server.v1_9_R1.World;
import net.minecraft.server.v1_9_R1.WorldServer;

public class SkellettTagEditTest {
	public static void setName(Player player, String name) throws Exception {
		EntityPlayer entity = ((CraftPlayer)player).getHandle();
		GameProfile profile = entity.getProfile();
		Field profilefield = profile.getClass().getDeclaredField("name");
		profilefield.setAccessible(true);
		profilefield.set((Object)profile, name);
		MinecraftServer nmsServer = ((CraftServer)Bukkit.getServer()).getServer();
		WorldServer nmsWorld = ((CraftWorld)Bukkit.getWorlds().get(0)).getHandle();
		EntityPlayer pNeu = new EntityPlayer(nmsServer, nmsWorld, new GameProfile(player.getUniqueId(), name), new PlayerInteractManager((World)nmsWorld));
		EntityPlayer pl = new EntityPlayer(nmsServer, nmsWorld, new GameProfile(player.getUniqueId(), player.getCustomName()), new PlayerInteractManager((World)nmsWorld));
		for (Player players : Bukkit.getOnlinePlayers()) {
			PlayerConnection connection = ((CraftPlayer)players).getHandle().playerConnection;
			connection.sendPacket((Packet<?>)new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, new EntityPlayer[]{pl}));
			connection.sendPacket((Packet<?>)new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, new EntityPlayer[]{pNeu}));
		}
	}
}