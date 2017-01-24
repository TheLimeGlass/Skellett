package com.gmail.thelimeglass.Nametags;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[skellett] remove %player% from [the] [name][ ]tag [(with|of)] [id] %string%")
@Config("Main.Nametags")
@FullConfig
public class EffRemovePlayerNametag extends Effect {
	
	private Expression<Player> player;
	private Expression<String> nametag;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		nametag = (Expression<String>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] remove %player% from [the] [name][ ]tag [(with|of)] [id] %string%";
	}
	@Override
	protected void execute(Event e) {
		SkellettNametags.removePlayerNametag(player.getSingle(e), nametag.getSingle(e));
	}
}
