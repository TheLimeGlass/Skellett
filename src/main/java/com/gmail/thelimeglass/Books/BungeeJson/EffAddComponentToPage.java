package com.gmail.thelimeglass.Books.BungeeJson;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BookMeta;

import com.gmail.thelimeglass.Utils.ReflectionUtil;
import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

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
	@SuppressWarnings("unchecked")
	@Override
	protected void execute(Event e) {
		ItemStack item = book.getSingle(e);
		if (item == null)
			return;
		BookMeta bookMeta = (BookMeta) item.getItemMeta();
		try {
			List<Object> pages = (List<Object>) ReflectionUtil.getOBCClass("inventory.CraftMetaBook").getDeclaredField("pages").get(bookMeta);
			Class<?> chatSerializer = ReflectionUtil.getNMSClass("IChatBaseComponent$ChatSerializer");
			Object page = chatSerializer.getMethod("a", String.class).invoke(chatSerializer, ComponentSerializer.toString(component.getAll(e)));
			if (pages != null) pages.add(page);
			book.getSingle(e).setItemMeta(bookMeta);
		} catch (ClassNotFoundException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NoSuchFieldException error) {
			error.printStackTrace();
		}
	}
}