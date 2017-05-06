package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[the] [skellett] skull[ ]owner of [skull] %block%", "[skellett] [skull] %block%'s skull[ ]owner"})
@Config("SkullOwner")
@PropertyType(ExpressionType.COMBINED)
public class ExprSkullBlockOwner extends SimpleExpression<OfflinePlayer>{
	
	private Expression<Block> skull;
	@Override
	public Class<? extends OfflinePlayer> getReturnType() {
		return OfflinePlayer.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		skull = (Expression<Block>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] skull[ ]owner of [skull] %block%";
	}
	@Override
	@Nullable
	protected OfflinePlayer[] get(Event e) {
		BlockState state = skull.getSingle(e).getState();
		if (state instanceof Skull) {
			Skull skull = (Skull) state;
			return new OfflinePlayer[]{skull.getOwningPlayer()};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			BlockState state = skull.getSingle(e).getState();
			if (state instanceof Skull) {
				Skull skull = (Skull) state;
				skull.setOwningPlayer((OfflinePlayer)delta[0]);
				skull.update();
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(OfflinePlayer.class);
		}
		return null;
	}
}