package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[current [inventory]] cursor of %player%", "%player%'s [current [inventory]] cursor"})
@Config("PlayerInventoryCursor")
@PropertyType("COMBINED")
public class ExprPlayerInventoryCursor extends SimpleExpression<ItemType> {
	
	private Expression<Player> player;
	public Class<? extends ItemType> getReturnType() {
		return ItemType.class;
	}
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		return true;
	}
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "[current [inventory]] cursor of %player%";
	}
	@Nullable
	protected ItemType[] get(Event e) {
		ItemType item = new ItemType(player.getSingle(e).getOpenInventory().getCursor());
		return new ItemType[]{item};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			ItemType item = (ItemType)delta[0];
			ItemStack m = item.getRandom();
			player.getSingle(e).getOpenInventory().setCursor(m);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(ItemType.class);
		}
		return null;
	}
}