package com.gmail.thelimeglass.Books;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

public class ExprBookTitle extends SimpleExpression<String>{
	
	//[the] [book['s]] title of %itemstack%
	//%itemstack%'s [book] title
	
	private Expression<ItemStack> item;
	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		item = (Expression<ItemStack>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] [book['s]] title of %itemstack%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		BookMeta book = (BookMeta) item.getSingle(e).getItemMeta();
		return new String[]{book.getTitle()};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		BookMeta book = (BookMeta) item.getSingle(e).getItemMeta();
		if (mode == ChangeMode.SET) {
			book.setTitle((String)delta[0]);
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET)
			return CollectionUtils.array(String.class);
		return null;
	}
}