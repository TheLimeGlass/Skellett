package com.gmail.thelimeglass.Books;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.Disabled;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("add [a] page [with] [(text|data)] [%-string%] to [book] %itemstack%")
@Config("Main.Books")
@FullConfig
@Disabled
public class EffAddJSONPage extends Effect {
	
	private Expression<String> string;
	private Expression<ItemStack> book;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		string = (Expression<String>) e[0];
		book = (Expression<ItemStack>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "add [a] page [with] [(text|data)] [%-string%] to [book] %itemstack%";
	}
	@Override
	protected void execute(Event e) {
		BookMeta bookMeta = (BookMeta) book.getSingle(e).getItemMeta();
		if (string != null) {
			bookMeta.addPage(string.getSingle(e).split("||"));
		} else {
			bookMeta.addPage();
		}
		book.getSingle(e).setItemMeta(bookMeta);
	}
}