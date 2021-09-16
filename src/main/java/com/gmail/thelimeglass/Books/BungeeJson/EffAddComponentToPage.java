package com.gmail.thelimeglass.Books.BungeeJson;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.md_5.bungee.api.chat.TextComponent;

@Syntax("add text component %textcomponents% to [book] %itemstack%")
@Config("Main.ChatComponent")
@FullConfig
public class EffAddComponentToPage extends Effect {
	
	private Expression<TextComponent> component;
	private Expression<ItemStack> book;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		component = (Expression<TextComponent>) e[0];
		book = (Expression<ItemStack>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "add text component %textcomponents% to [book] %itemstack%";
	}
	@Override
	protected void execute(Event e) {
		ItemStack item = book.getSingle(e);
		if (item == null)
			return;
		BookMeta bookMeta = (BookMeta) item.getItemMeta();
		bookMeta.spigot().addPage(component.getAll(e));
		item.setItemMeta(bookMeta);
	}
}