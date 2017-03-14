package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.metadata.Metadatable;

import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax("[(skellett|fixed)] meta[ ]data [value] %string% (of|in|within) %object%")
@Config("Metadata")
@PropertyType(ExpressionType.COMBINED)
public class ExprMetadata extends SimpleExpression<Object>{
	
	private Expression<String> string;
	private Expression<Object> object;
	@Override
	public Class<? extends Object> getReturnType() {
		return Object.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		string = (Expression<String>) e[0];
		object = (Expression<Object>) e[1];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[(skellett|fixed)] meta[ ]data [value] %string% (of|in|within) %object%";
	}
	@Override
	@Nullable
	protected Object[] get(Event e) {	
		Metadatable metadata;
		if (object.getSingle(e) instanceof Metadatable && (metadata = (Metadatable)object.getSingle(e)).hasMetadata(string.getSingle(e))) {
			return new Object[]{((MetadataValue)metadata.getMetadata(string.getSingle(e)).iterator().next()).value()};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (object.getSingle(e) instanceof Metadatable) {
			Metadatable metadata = (Metadatable)object.getSingle(e);
			if (mode == ChangeMode.SET) {
				if (metadata.hasMetadata(string.getSingle(e))) {
					metadata.removeMetadata(string.getSingle(e), Skellett.plugin);
				}
				metadata.setMetadata(string.getSingle(e), (MetadataValue)new FixedMetadataValue(Skellett.plugin, (Object)(delta[0])));
			} else if (mode == ChangeMode.RESET || mode == ChangeMode.DELETE) {
				if (metadata.hasMetadata(string.getSingle(e))) {
					metadata.removeMetadata(string.getSingle(e), Skellett.plugin);
				}
			}
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET || mode == ChangeMode.RESET || mode == ChangeMode.DELETE) {
			return CollectionUtils.array(Object.class);
		}
		return null;
	}
}