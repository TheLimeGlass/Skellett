package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Jukebox;
import org.bukkit.event.Event;
import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.Disabled;
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

@Syntax({"[skellett] [the] (record|track|song) playing (in|inside|within|of|from) juke[ ]box %block%", "[skellett] %block%'s (record|track|song) playing"})
@Config("Main.Jukebox")
@FullConfig
@Disabled
@PropertyType(ExpressionType.COMBINED)
public class ExprJukeboxMusic extends SimpleExpression<Material>{
	
	private Expression<Block> block;
	@Override
	public Class<? extends Material> getReturnType() {
		return Material.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		block = (Expression<Block>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[skellett] [the] (record|track|song) playing (in|inside|within|of|from) jukebox %block%";
	}
	@Override
	@Nullable
	protected Material[] get(Event e) {
		if (block != null) {
			if (block.getSingle(e) instanceof Jukebox) {
				return new Material[]{((Jukebox)block.getSingle(e)).getPlaying()};
			}
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			if (block != null) {
				if (block.getSingle(e) instanceof Jukebox) {
					String type = (String)delta[0];
					try {
						Material material = Material.valueOf(type.replace("\"", "").trim().replace(" ", "_").toUpperCase());
						((Jukebox)block.getSingle(e)).setPlaying(material);
					} catch (IllegalArgumentException error) {
						Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cUnknown material type " + type));
						return;
					}
				}
			}
		} else if (mode == ChangeMode.RESET) {
			if (((Jukebox)block.getSingle(e)).isPlaying()) {
				((Jukebox)block.getSingle(e)).eject();
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET) {
			return CollectionUtils.array(String.class);
		}
		return null;
	}
}