package com.gmail.thelimeglass.ReflectionSyntax;

import java.lang.reflect.InvocationTargetException;
import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import com.gmail.thelimeglass.Utils.ReflectionUtil;
import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.FullConfig;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

@Syntax("open book %itemstack% to %players%")
@Config("Main.Books")
@FullConfig
public class EffOpenBook extends Effect {
	
	private Expression<ItemStack> book;
	private Expression<Player> players;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		book = (Expression<ItemStack>) e[0];
		players = (Expression<Player>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "open book %itemstack% to %players%";
	}
	@Override
	protected void execute(Event e) {
		ItemStack bookItem = book.getSingle(e);
		if (bookItem == null)
			return;
		if (Skript.methodExists(Player.class, "openBook", ItemStack.class)) {
			for (Player player : players.getArray(e))
				player.openBook(bookItem);
			return;
		}
		for (Player player : players.getAll(e)) {
			try {
				int slot = player.getInventory().getHeldItemSlot();
				ItemStack item = player.getInventory().getItem(slot);
				player.getInventory().setItem(slot, bookItem);
				ByteBuf buf = Unpooled.buffer(256);
				buf.setByte(0, (byte)0);
				buf.writerIndex(1);
				Class<?> packet = ReflectionUtil.getNMSClass("PacketPlayOutCustomPayload");
				Class<?> dataSerializerPacket = ReflectionUtil.getNMSClass("PacketDataSerializer");
				Object dataSerializer = dataSerializerPacket.getConstructor(ByteBuf.class).newInstance(buf);
				Object constructor = packet.getConstructor(String.class, dataSerializerPacket).newInstance("MC|BOpen", dataSerializer);
				ReflectionUtil.sendPacket(player, constructor);
				player.getInventory().setItem(slot, item);
			} catch (SecurityException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException e1) {
				e1.printStackTrace();
			}
		}
	}
}