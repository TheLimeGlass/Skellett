package com.gmail.thelimeglass.versionControl;

import javax.annotation.Nullable;

import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.minecraft.server.v1_9_R2.IChatBaseComponent;
import net.minecraft.server.v1_9_R2.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_9_R2.PacketPlayOutChat;

public class EffActionbarv1_9_R2 extends Effect {
	
	//(send|show) [a[n]] action[ ]bar [(with|from)] [string] %string% to %players%
	
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
			IChatBaseComponent text = ChatSerializer.a("{\"text\": \"" + string.getSingle(e).replace("\"", "") + "\"}");
			PacketPlayOutChat packet = new PacketPlayOutChat(text, (byte) 2);
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
		}
	}
}