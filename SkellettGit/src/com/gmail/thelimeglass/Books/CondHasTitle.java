package com.gmail.thelimeglass.Books;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("book %itemstack% (1¦(ha(s[n[']t]|ve)|contain[s])|2¦(do[es](n't| not) have| do[es](n't| not) contain)) [had] [a] [book [meta]] title")
@Config("Main.Books")
@FullConfig
public class CondHasTitle extends Condition {
	
	private Expression<ItemStack> item;
	private Boolean boo = true;
	@SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		item = (Expression<ItemStack>) e[0];
		if (parser.mark == 2) {boo = false;}
		return true;
	}
	public String toString(@Nullable Event e, boolean arg1) {
		return "book %itemstack% (1¦(ha(s[n[']t]|ve)|contain[s])|2¦(do[es](n't| not) have| do[es](n't| not) contain)) [had] [a] [book [meta]] title";
	}
	public boolean check(Event e) {
		BookMeta book = (BookMeta) item.getSingle(e).getItemMeta();
		if (book.hasTitle()) {
			if (boo == true) {
				return true;
			} else {
				return false;
			}
		} else {
			if (boo == false) {
				return true;
			} else {
				return false;
			}
		}
	}
}