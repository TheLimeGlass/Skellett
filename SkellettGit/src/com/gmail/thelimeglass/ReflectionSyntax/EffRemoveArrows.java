package com.gmail.thelimeglass.ReflectionSyntax;

import javax.annotation.Nullable;

import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;
import com.gmail.thelimeglass.Utils.Annotations.Version;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import net.minecraft.server.v1_11_R1.DataWatcherObject;
import net.minecraft.server.v1_11_R1.DataWatcherRegistry;

@Syntax("remove [(the|all)] [of] [the] arrows stuck in %player%")
@Config("TitleAndSubtitle")
@Version("1.11.2")
public class EffRemoveArrows extends Effect {
	
	private Expression<Player> player;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		player = (Expression<Player>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "remove [(the|all)] [of] [the] arrows stuck in %player%";
	}
	@Override
	protected void execute(Event e) {
		((CraftPlayer)player.getSingle(e)).getHandle().getDataWatcher().set(new DataWatcherObject<>(10, DataWatcherRegistry.b),0);
		/*Player p = player.getSingle(e);
		try {
			Object player = ReflectionUtil.getHandle(p);
			Class<?> datawatcherObject = ReflectionUtil.getNMSClass("DataWatcherObject");
			Object datawatcher = player.getClass().getMethod("getDataWatcher").invoke(player);
			Method datawatcherSettable = datawatcher.getClass().getMethod("set", datawatcherObject, Integer.class);
			
			Class<?> datawatcherRegistry = ReflectionUtil.getNMSClass("DataWatcherRegistry");
			Object datawatcherSerializer = datawatcherRegistry.getClass().getField("b");
			
			Constructor<?> constructorDWObject = datawatcherObject.getConstructor(Integer.class, datawatcherSerializer.getClass());
			Object datawatcherFinal = constructorDWObject.newInstance(10, datawatcherSerializer);
			datawatcherSettable.invoke(datawatcher, datawatcherFinal, 0);
		} catch (InstantiationException | SecurityException | NoSuchMethodException | NoSuchFieldException | IllegalArgumentException | IllegalAccessException | InvocationTargetException | ClassNotFoundException error) {
			error.printStackTrace();
		}*/
	}
}