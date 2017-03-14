package com.gmail.thelimeglass.Effects;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.util.Color;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import javax.annotation.Nullable;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.RegisterSimpleEnum;
import com.gmail.thelimeglass.Utils.Syntax;

@Syntax("[skellett] apply [potion[s]] [of] %potioneffecttype% [potion] [[[of] tier] %-number%] to %livingentities% [for %-timespan%] [[and] ambient %-boolean% [hide [particle [effects] %-boolean% [colo[u]r %-color%]]]]")
@Config("BetterPotion")
@RegisterSimpleEnum(ExprClass=PotionEffectType.class, value="potioneffecttype")
public class EffApplyBetterPotion extends Effect {
	
	private Expression<PotionEffectType> potion;
	private Expression<Number> tier;
	private Expression<LivingEntity> entities;
	private Expression<Timespan> time;
	private Expression<Boolean> particles, ambient;
	private Expression<Color> colour;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		potion = (Expression<PotionEffectType>) e[0];
		tier = (Expression<Number>) e[1];
		entities = (Expression<LivingEntity>) e[2];
		time = (Expression<Timespan>) e[3];
		ambient = (Expression<Boolean>) e[4];
		particles = (Expression<Boolean>) e[5];
		colour = (Expression<Color>) e[6];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] apply [potion[s]] [of] %potioneffecttype% [potion] [[[of] tier] %-number%] to %livingentities% [for %-timespan%] [[and] ambient %-boolean% [hide [particle [effects] %-boolean% [colo[u]r %-color%]]]]";
	}
	@Override
	protected void execute(Event e) {
		if (potion != null && entities != null) {
			PotionEffect p = new PotionEffect(potion.getSingle(e), 300, 1);
			if (time == null && tier == null && ambient == null) {
				p = new PotionEffect(potion.getSingle(e), 300, 1);
			} else if (time == null && tier == null && ambient != null) {
				p = new PotionEffect(potion.getSingle(e), 300, 1, particles.getSingle(e));
			} else if (time == null && tier != null && ambient != null) {
				if (particles == null && colour == null) {
					p = new PotionEffect(potion.getSingle(e), 300, tier.getSingle(e).intValue(), ambient.getSingle(e));
				} else if (particles != null && colour == null) {
					p = new PotionEffect(potion.getSingle(e), 300, tier.getSingle(e).intValue(), ambient.getSingle(e), particles.getSingle(e));
				} else if (particles != null && colour != null) {
					p = new PotionEffect(potion.getSingle(e), 300, tier.getSingle(e).intValue(), ambient.getSingle(e), particles.getSingle(e), colour.getSingle(e).getBukkitColor());
				}
			} else if (time != null && tier != null && ambient != null) {
				Number ticks = Skellett.getTicks(time.getSingle(e));
				if (particles == null && colour == null) {
					p = new PotionEffect(potion.getSingle(e), ticks.intValue(), tier.getSingle(e).intValue(), ambient.getSingle(e));
				} else if (particles != null && colour == null) {
					p = new PotionEffect(potion.getSingle(e), ticks.intValue(), tier.getSingle(e).intValue(), ambient.getSingle(e), particles.getSingle(e));
				} else if (particles != null && colour != null) {
					p = new PotionEffect(potion.getSingle(e), ticks.intValue(), tier.getSingle(e).intValue(), ambient.getSingle(e), particles.getSingle(e), colour.getSingle(e).getBukkitColor());
				}
			}
			for (LivingEntity entity : entities.getAll(e)) {
				p.apply(entity);
			}
		}
	}
}
