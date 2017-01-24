package com.gmail.thelimeglass.versionControl;

import javax.annotation.Nullable;

import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.DetectVersion;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import net.minecraft.server.v1_9_R1.IChatBaseComponent;
import net.minecraft.server.v1_9_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_9_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_9_R1.PacketPlayOutTitle.EnumTitleAction;

@Syntax({"[skellett] (send|show) [a] (1¦subtitle|2¦title) [from] %string% [(with|and) [subtitle] %-string%] (to|for) %players% for %timespan%(,| and| with) %timespan% [fade[ ]in](,| and| with) %timespan% [fade[ ]out]", "[skellett] (send|show) %players% [a] (1¦subtitle|2¦title) [(with|from)] %string% [(with|and) [subtitle] %-string%] for %timespan%(,| and| with) %timespan% [fade[ ]in](,| and| with) %timespan% [fade[ ]out]"})
@Config("TitleAndSubtitle")
@DetectVersion
public class EffTitlev1_9_R1 extends Effect {

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
		return "[skellett] (send|show) [a] title [(with|from)] %string% [(with|and) [sub[ ]title] %-string%] (to|for) %players% for %timespan%(,| and| with [fade[ ]in]) %timespan% [fade[ ]in](,| and|( with| and) [fade[ ]out]) %timespan% [fade[ ]out]";
	}
	@Override
	protected void execute(Event e) {
		int ticks = 60;
		if (time != null) {
			ticks = Skellett.getTicks(time.getSingle(e));
		}
		PacketPlayOutTitle packetTime = new PacketPlayOutTitle(EnumTitleAction.TIMES, null, 5, ticks, 5);
		if (fadeIn != null && fadeOut != null) {
			packetTime = new PacketPlayOutTitle(EnumTitleAction.TIMES, null, Skellett.getTicks(fadeIn.getSingle(e)), ticks, Skellett.getTicks(fadeOut.getSingle(e)));
		} else if (fadeIn != null && fadeOut == null) {
			packetTime = new PacketPlayOutTitle(EnumTitleAction.TIMES, null, Skellett.getTicks(fadeIn.getSingle(e)), ticks, 5);
		} else if (fadeIn == null && fadeOut != null) {
			packetTime = new PacketPlayOutTitle(EnumTitleAction.TIMES, null, 5, ticks, Skellett.getTicks(fadeOut.getSingle(e)));
		}
		for (Player p : players.getAll(e)) {
			((CraftPlayer) p.getPlayer()).getHandle().playerConnection.sendPacket(packetTime);
		}
		if (isSubtitle == true) {
			IChatBaseComponent text = ChatSerializer.a("{\"text\": \"" + title.getSingle(e) + "\"}");
			PacketPlayOutTitle finalTitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, text);
			for (Player p : players.getAll(e)) {
				((CraftPlayer) p.getPlayer()).getHandle().playerConnection.sendPacket(finalTitle);
			}
			IChatBaseComponent textF = ChatSerializer.a("{\"text\": \"" + "" + "\"}");
			PacketPlayOutTitle finalTitleF = new PacketPlayOutTitle(EnumTitleAction.TITLE, textF);
			for (Player p : players.getAll(e)) {
				((CraftPlayer) p.getPlayer()).getHandle().playerConnection.sendPacket(finalTitleF);
			}
		} else {
			if (subtitle != null) {
				IChatBaseComponent text = ChatSerializer.a("{\"text\": \"" + subtitle.getSingle(e) + "\"}");
				PacketPlayOutTitle finalSubTitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, text);
				for (Player p : players.getAll(e)) {
					((CraftPlayer) p.getPlayer()).getHandle().playerConnection.sendPacket(finalSubTitle);
				}
			}
			IChatBaseComponent text = ChatSerializer.a("{\"text\": \"" + title.getSingle(e) + "\"}");
			PacketPlayOutTitle finalTitle = new PacketPlayOutTitle(EnumTitleAction.TITLE, text);
			for (Player p : players.getAll(e)) {
				((CraftPlayer) p.getPlayer()).getHandle().playerConnection.sendPacket(finalTitle);
			}
		}
	}
}