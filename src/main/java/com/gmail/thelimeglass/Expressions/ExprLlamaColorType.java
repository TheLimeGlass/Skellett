package com.gmail.thelimeglass.Expressions;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Llama;
import org.bukkit.event.Event;
import org.eclipse.jdt.annotation.Nullable;

import com.gmail.thelimeglass.Utils.Annotations.Config;
import com.gmail.thelimeglass.Utils.Annotations.PropertyType;
import com.gmail.thelimeglass.Utils.Annotations.RegisterEnum;
import com.gmail.thelimeglass.Utils.Annotations.Syntax;
import com.gmail.thelimeglass.Utils.Annotations.Version;

import ch.njol.skript.classes.Changer;
import ch.njol.skript.classes.Changer.ChangeMode;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;

@Syntax({"Llama colo[u]r of %entity%", "Llama %entity%'s colo[u]r", "%entity%['s] Llama colo[u]r"})
@Config("LlamaColour")
@PropertyType(ExpressionType.COMBINED)
@Version("1.11")
@RegisterEnum("llamacolor")
public class ExprLlamaColorType extends SimpleExpression<Llama.Color>{
	
	private Expression<Entity> llama;
	@Override
	public Class<? extends Llama.Color> getReturnType() {
		return Llama.Color.class;
	}
	@Override
	public boolean isSingle() {
		return true;
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		llama = (Expression<Entity>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "Llama colo[u]r of %entity%";
	}
	@Override
	protected Llama.Color[] get(Event e) {
		if (llama.getSingle(e) instanceof Llama) {
			return new Llama.Color[]{((Llama) llama.getSingle(e)).getColor()};
		}
		return null;
	}
	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode){
		if (mode == ChangeMode.SET) {
			if (!(llama.getSingle(e) instanceof Llama)) {
				return;
			}
			((Llama) llama.getSingle(e)).setColor((Llama.Color)delta[0]);
		}
	}
	@Override
	public Class<?>[] acceptChange(final Changer.ChangeMode mode) {
		if (mode == ChangeMode.SET) {
			return CollectionUtils.array(Llama.Color.class);
		}
		return null;
	}
}