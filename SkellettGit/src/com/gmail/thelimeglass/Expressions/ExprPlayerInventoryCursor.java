package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[current [inventory]] cursor of %player%", "%player%'s [current [inventory]] cursor"})
@Config("PlayerInventoryCursor")
@PropertyType(ExpressionType.COMBINED)
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
		if (player.getSingle(e).getOpenInventory().getCursor() != null) {
			return new ItemType[]{item};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			ItemType item = (ItemType)delta[0];
			ItemStack m = item.getRandom();
			player.getSingle(e).getOpenInventory().setCursor(m);
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(ItemType.class);
		}
		return null;
	}
}