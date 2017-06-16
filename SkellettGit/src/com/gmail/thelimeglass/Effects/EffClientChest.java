package com.gmail.thelimeglass.Effects;

import java.lang.reflect.InvocationTargetException;
import javax.annotation.Nullable;

import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.ReflectionUtil;
import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax({"[skellett] (1她pen|2圭lose) [the] %block% for %players%", "[skellett] make [the] %block% (appear|look) (1她pen|2圭losed) for %players%", "[skellett] play chest (1她pen|2圭lose) animation at %block% for %players%"})
@Config("ClientChest")
public class EffClientChest extends Effect {
	
	private Integer marker;
	private Expression<Player> players;
	private Expression<Block> block;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		block = (Expression<Block>) e[0];
		players = (Expression<Player>) e[1];
		marker = parser.mark;
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] make [the] %block% (appear|look) (1她pen|2圭losed) for %players%";
	}
	@Override
	protected void execute(Event e) {
		try {
			Object nmsBlock = ReflectionUtil.getNMSBlock(block.getSingle(e));
			Class<?> blockLoc = ReflectionUtil.getNMSClass("BlockPosition");
			Object blockLocation = blockLoc
				.getConstructor(Integer.TYPE, Integer.TYPE, Integer.TYPE)
				.newInstance(block.getSingle(e).getX(), block.getSingle(e).getY(), block.getSingle(e).getZ());
			Class<?> blockPacket = ReflectionUtil.getNMSClass("PacketPlayOutBlockAction");
			Object packet = blockPacket
				.getConstructor(blockLoc, ReflectionUtil.getNMSClass("Block"), Integer.TYPE, Integer.TYPE)
				.newInstance(blockLocation, nmsBlock, 1, 1);
			if (marker == 2) {
				packet = blockPacket
					.getConstructor(blockLoc, ReflectionUtil.getNMSClass("Block"), Integer.TYPE, Integer.TYPE)
					.newInstance(blockLocation, nmsBlock, 1, 0);
			}
			for (Player p : players.getAll(e)) {
				ReflectionUtil.sendPacket(p, packet);
			}
		} catch (SecurityException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
}