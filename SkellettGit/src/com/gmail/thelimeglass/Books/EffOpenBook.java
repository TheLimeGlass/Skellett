package com.gmail.thelimeglass.Books;

import java.lang.reflect.InvocationTargetException;
import javax.annotation.Nullable;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.ReflectionUtil;
import com.gmail.thelimeglass.Utils.Syntax;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

@Syntax("open book %itemstack% to %player%")
@Config("Main.Books")
@FullConfig
public class EffOpenBook extends Effect {
	
	private Expression<ItemStack> book;
	private Expression<Player> player;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		book = (Expression<ItemStack>) e[0];
		player = (Expression<Player>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "open book %itemstack% to %player%";
	}
	@Override
	protected void execute(Event e) {
		try {
			int slot = player.getSingle(e).getInventory().getHeldItemSlot();
			ItemStack item = player.getSingle(e).getInventory().getItem(slot);
			player.getSingle(e).getInventory().setItem(slot, book.getSingle(e));
			ByteBuf buf = Unpooled.buffer(256);
			buf.setByte(0, (byte)0);
			buf.writerIndex(1);
			Class<?> packet = ReflectionUtil.getNMSClass("PacketPlayOutCustomPayload");
			Class<?> dataSerializerPacket = ReflectionUtil.getNMSClass("PacketDataSerializer");
			Object dataSerializer = dataSerializerPacket.getConstructor(ByteBuf.class).newInstance(buf);
			Object constructor = packet.getConstructor(String.class, dataSerializerPacket).newInstance("MC|BOpen", dataSerializer);
			ReflectionUtil.sendPacket(player.getSingle(e), constructor);
			player.getSingle(e).getInventory().setItem(slot, item);
		} catch (SecurityException | NoSuchMethodException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | InstantiationException | ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}
}