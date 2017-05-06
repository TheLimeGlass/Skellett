package com.gmail.thelimeglass.Expressions;

import javax.annotation.Nullable;

import org.bukkit.Particle;
import org.bukkit.event.Event;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.FullConfig;
import com.gmail.thelimeglass.Utils.PropertyType;
import com.gmail.thelimeglass.Utils.Syntax;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

@Syntax({"[the] particle data of %particle%", "[the] %particle%'s particle data"})
@Config("Syntax.Effects.Particles")
@FullConfig
@PropertyType(ExpressionType.COMBINED)
public class ExprParticleData extends SimpleExpression<Object>{
	
	private Expression<Particle> particle;
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
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		particle = (Expression<Particle>) e[0];
		return true;
	}
	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "[the] particle data of %particle%";
	}
	@Override
	@Nullable
	protected Object[] get(Event e) {
		return new Object[]{particle.getSingle(e).getDataType()};
	}
}