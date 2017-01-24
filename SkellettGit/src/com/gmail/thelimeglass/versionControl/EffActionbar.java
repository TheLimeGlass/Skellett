package com.gmail.thelimeglass.versionControl;

import java.lang.reflect.Constructor;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.ReflectionUtil;
import com.gmail.thelimeglass.Utils.Syntax;

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
		for (Player p : players.getAll(e)) {
			try {
				Class<?> chatSerializer = ReflectionUtil.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0];
				Class<?> chatComponent = ReflectionUtil.getNMSClass("IChatBaseComponent");
				Constructor<?> ConstructorActionbar = ReflectionUtil.getNMSClass("PacketPlayOutChat").getDeclaredConstructor(chatComponent, byte.class);
				Object actionbar = chatSerializer.getMethod("a", String.class).invoke(chatSerializer, "{\"text\": \"" + string.getSingle(e).replace("\"", "") + "\"}");
				Object packet = ConstructorActionbar.newInstance(actionbar, (byte) 2);
				ReflectionUtil.sendPacket(p, packet);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
}