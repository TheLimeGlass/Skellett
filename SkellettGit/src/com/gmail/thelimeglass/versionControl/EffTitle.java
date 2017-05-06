package com.gmail.thelimeglass.versionControl;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.ReflectionUtil;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;

@Syntax({"[skellett] (send|show) [a] (1¦subtitle|2¦title) [from] %string% [(with|and) [subtitle] %-string%] (to|for) %players% for %timespan%(,| and| with) %timespan% [fade[ ]in](,| and| with) %timespan% [fade[ ]out]", "[skellett] (send|show) %players% [a] (1¦subtitle|2¦title) [(with|from)] %string% [(with|and) [subtitle] %-string%] for %timespan%(,| and| with) %timespan% [fade[ ]in](,| and| with) %timespan% [fade[ ]out]"})
@Config("TitleAndSubtitle")
public class EffTitle extends Effect {
	
	private Expression<String> title, subtitle;
	private Expression<Timespan> time, fadeIn, fadeOut;
	private Expression<Player> players;
	private Boolean isSubtitle = false;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		if (parser.mark == 1) {isSubtitle = true;}
		if (matchedPattern == 0) {
			title = (Expression<String>) e[0];
			subtitle = (Expression<String>) e[1];
			players = (Expression<Player>) e[2];
		} else {
			players = (Expression<Player>) e[0];
			title = (Expression<String>) e[1];
			subtitle = (Expression<String>) e[2];
		}
		time = (Expression<Timespan>) e[3];
		fadeIn = (Expression<Timespan>) e[4];
		fadeOut = (Expression<Timespan>) e[5];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] (send|show) [a] (1¦sub[ ]title|2¦title) [(with|from)] %string% [(with|and) [sub[ ]title] %-string%] (to|for) %players% for %timespan%(,| and| with [fade[ ]in]) %timespan% [fade[ ]in](,| and|( with| and) [fade[ ]out]) %timespan% [fade[ ]out]";
	}
	@Override
	protected void execute(Event e) {
		int ticks = 60;
		if (time != null) {
			ticks = Skellett.getTicks(time.getSingle(e));
		}
		try {
			Class<?> packetAction = ReflectionUtil.getNMSClass("PacketPlayOutTitle$EnumTitleAction");
			Class<?> chatComponent = ReflectionUtil.getNMSClass("IChatBaseComponent");
			Class<?> packetTitle = ReflectionUtil.getNMSClass("PacketPlayOutTitle");
			Class<?> chatSerializer = ReflectionUtil.getNMSClass("IChatBaseComponent").getDeclaredClasses()[0];
			Constructor<?> ConstructorTitle = packetTitle.getConstructor(packetAction, chatComponent, Integer.TYPE, Integer.TYPE, Integer.TYPE);
			Object[] actions = packetAction.getEnumConstants();
			
			Object packetTime = ConstructorTitle.newInstance(actions[3], null, 5, ticks, 5);
			if (fadeIn != null && fadeOut != null) {
				packetTime = ConstructorTitle.newInstance(actions[3], null, Skellett.getTicks(fadeIn.getSingle(e)), ticks, Skellett.getTicks(fadeOut.getSingle(e)));
			} else if (fadeIn != null && fadeOut == null) {
				packetTime = ConstructorTitle.newInstance(actions[3], null, Skellett.getTicks(fadeIn.getSingle(e)), ticks, 5);
			} else if (fadeIn == null && fadeOut != null) {
				packetTime = ConstructorTitle.newInstance(actions[3], null, 5, ticks, Skellett.getTicks(fadeOut.getSingle(e)));
			}
			for (Player player : players.getAll(e)) {
				ReflectionUtil.sendPacket(player, packetTime);
			}
			
			if (isSubtitle == true) {
				Object text = chatSerializer.getMethod("a", String.class).invoke(chatSerializer, "{\"text\": \"" + title.getSingle(e) + "\"}");
				Object finalTitle = packetTitle.getConstructor(packetAction, chatComponent).newInstance(actions[1], text);
				for (Player player : players.getAll(e)) {
					ReflectionUtil.sendPacket(player, finalTitle);
				}
				Object textF = chatSerializer.getMethod("a", String.class).invoke(chatSerializer, "{\"text\": \"" + "" + "\"}");
				Object finalTitleF = packetTitle.getConstructor(packetAction, chatComponent).newInstance(actions[0], textF);
				for (Player player : players.getAll(e)) {
					ReflectionUtil.sendPacket(player, finalTitleF);
				}
			} else {
				if (subtitle != null) {
					Object text = chatSerializer.getMethod("a", String.class).invoke(chatSerializer, "{\"text\": \"" + subtitle.getSingle(e) + "\"}");
					Object finalSubTitle = packetTitle.getConstructor(packetAction, chatComponent).newInstance(actions[1], text);
					for (Player player : players.getAll(e)) {
						ReflectionUtil.sendPacket(player, finalSubTitle);
					}
				}
				Object text = chatSerializer.getMethod("a", String.class).invoke(chatSerializer, "{\"text\": \"" + title.getSingle(e) + "\"}");
				Object finalTitle = packetTitle.getConstructor(packetAction, chatComponent).newInstance(actions[0], text);
				for (Player player : players.getAll(e)) {
					ReflectionUtil.sendPacket(player, finalTitle);
				}
			}
		} catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | SecurityException | InstantiationException error) {
			error.printStackTrace();
		}
	}
}