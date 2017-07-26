package com.gmail.thelimeglass.Eggwars;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.MainConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.RegisterSimpleEnum;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import es.minetsii.eggwars.objects.EwPlayer;
import es.minetsii.eggwars.objects.InvPlayer;

@Syntax({"[the] egg[ ]wars inv[entory] of player %ewplayer%", "%player%'s egg[ ]war[s] inv[entory]"})
@Config("PluginHooks.Eggwars")
@FullConfig
@MainConfig
@PropertyType(ExpressionType.COMBINED)
@RegisterSimpleEnum(ExprClass=InvPlayer.class, value="ewinv")
public class ExprEggwarsPlayerArena extends SimpleExpression<InvPlayer>{
	
	private Expression<EwPlayer> player;
	@Override
	public Class<? extends InvPlayer> getReturnType() {
		return InvPlayer.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		player = (Expression<EwPlayer>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] egg[ ]wars inv[entory] of player %ewplayer%";
	}
	@Override
	@Nullable
	protected InvPlayer[] get(Event e) {
		if (player != null) {
			return new InvPlayer[]{player.getSingle(e).getInv()};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			if (player != null) {
				player.getSingle(e).setInv((InvPlayer)delta[0]);
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(InvPlayer.class);
		return null;
	}
}