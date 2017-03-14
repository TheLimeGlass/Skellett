package com.gmail.thelimeglass.Effects;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.InventoryView;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.RegisterEnum;
import com.gmail.thelimeglass.Utils.Syntax;

@Syntax("(set|change) %player%['s] (window|[current] inventory) property [of] %inventoryproperty% to %number%")
@Config("PlayerWindowProperty")
@RegisterEnum(ExprClass=InventoryView.Property.class, value="inventoryproperty")
public class EffSetInventoryProperty extends Effect {
	
	private Expression<Player> player;
	private Expression<InventoryView.Property> property;
	private Expression<Number> number;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		player = (Expression<Player>) e[0];
		property = (Expression<InventoryView.Property>) e[1];
		number = (Expression<Number>) e[2];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "(set|change) %player%['s] (window|[current] inventory) property [of] %inventoryproperty% to %number%";
	}
	@Override
	protected void execute(Event e) {
		player.getSingle(e).getOpenInventory().setProperty(property.getSingle(e), number.getSingle(e).intValue());
	}
}
