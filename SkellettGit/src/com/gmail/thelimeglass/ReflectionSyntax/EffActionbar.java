package com.gmail.thelimeglass.ReflectionSyntax;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.ReflectionUtil;
import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("(send|show) [a[n]] action[ ]bar [(with|from)] [string] %string% to %players%")
@Config("Actionbar")
public class EffActionbar extends Effect {
	
	private Expression<String> string;
	private Expression<Player> players;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		string = (Expression<String>) e[0];
		players = (Expression<Player>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(send|show) [a[n]] action[ ]bar [(with|from)] [string] %string% to %players%";
	}
	@Override
	protected void execute(Event e) {
		if (Bukkit.getServer().getVersion().contains("MC: 1.8") || Bukkit.getServer().getVersion().contains("MC: 1.9") || 
				Bukkit.getServer().getVersion().contains("MC: 1.10") || Bukkit.getServer().getVersion().contains("MC: 1.11")) {
			try {
				Class<?> chatSerializer = ReflectionUtil.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0];
				Class<?> chatComponent = ReflectionUtil.getNMSClass("IChatBaseComponent");
				Constructor<?> ConstructorActionbar = ReflectionUtil.getNMSClass("PacketPlayOutChat").getDeclaredConstructor(chatComponent, byte.class);
				Object actionbar = chatSerializer.getMethod("a", String.class).invoke(chatSerializer, "{\"text\": \"" + string.getSingle(e).replace("\"", "") + "\"}");
				Object packet = ConstructorActionbar.newInstance(actionbar, (byte) 2);
				for (Player p : players.getAll(e)) {
					ReflectionUtil.sendPacket(p, packet);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			try {
				Constructor<?> chatConstructor = ReflectionUtil.getNMSClass("ChatComponentText").getConstructor(String.class);
				Object chatComponent = chatConstructor.newInstance(org.bukkit.ChatColor.translateAlternateColorCodes('&', string.getSingle(e)));
				Object messageType = ReflectionUtil.getNMSClass("ChatMessageType").getField("GAME_INFO").get(null);
				Object packet = ReflectionUtil.getNMSClass("PacketPlayOutChat").getConstructor(new Class[] {ReflectionUtil.getNMSClass("IChatBaseComponent"), ReflectionUtil.getNMSClass("ChatMessageType")}).newInstance(new Object[] {chatComponent, messageType});
				for (Player p : players.getAll(e)) {
					ReflectionUtil.sendPacket(p, packet);
				}
			} catch (NoSuchMethodException | SecurityException | ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e1) {
				e1.printStackTrace();
			}
		}
	}
}