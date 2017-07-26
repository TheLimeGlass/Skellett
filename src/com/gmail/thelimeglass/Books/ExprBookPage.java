package com.gmail.thelimeglass.Books;

import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"[the] [book['s]] page %number% (of|in) [book] %itemstack%", "%itemstack%'s [book] page %number%"})
@Config("Main.Books")
@FullConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprBookPage extends SimpleExpression<String>{
	
	private Expression<Number> page;
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
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		if (matchedPattern == 0) {
			page = (Expression<Number>) e[0];
			item = (Expression<ItemStack>) e[1];
		} else{
			item = (Expression<ItemStack>) e[0];
			page = (Expression<Number>) e[1];
		}
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] [book['s]] page %number% (of|in) %itemstack%";
	}
	@Override
	@Nullable
	protected String[] get(Event e) {
		BookMeta book = (BookMeta) item.getSingle(e).getItemMeta();
		Number num = page.getSingle(e);
		return new String[]{book.getPage(num.intValue())};
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		BookMeta book = (BookMeta) item.getSingle(e).getItemMeta();
		Number num = page.getSingle(e);
		if (mode == ChangeMode.SET || mode == ChangeMode.ADD) {
			book.setPage(num.intValue(), (String)delta[0]);
			item.getSingle(e).setItemMeta(book);
		} else if (mode == ChangeMode.DELETE) {
			List<String> pages = book.getPages();
			pages.remove(num.intValue());
			book.setPages(pages);
			item.getSingle(e).setItemMeta(book);
		} else if (mode == ChangeMode.RESET) {
			List<String> pages = book.getPages();
			pages.set(num.intValue(), "");
			book.setPages(pages);
			item.getSingle(e).setItemMeta(book);
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.DELETE || mode == ChangeMode.RESET || mode == ChangeMode.ADD) {
			return CollectionUtils.array(String.class);
		}
		return null;
	}
}