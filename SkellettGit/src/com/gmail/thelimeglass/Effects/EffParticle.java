package com.gmail.thelimeglass.Effects;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.event.Event;

import com.gmail.thelimeglass.Skellett;
import com.gmail.thelimeglass.Utils.Config;
import com.gmail.thelimeglass.Utils.Syntax;
import com.gmail.thelimeglass.Utils.Version;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

@Syntax("[skellett] (create|place|spawn|play|make|summon) [%-number%] [of] (1¦%-particle%|2¦%-string%) [particle[s]] at %locations% [[with] [offset] [by] %-number%[(,| and)] %-number%[(,| and)] %-number%]")
@Config("Particles")
@Version("1.9R1")
public class EffParticle extends Effect {
	
	private Expression<Number> count, offsetX, offsetY, offsetZ;
	private Expression<Particle> particle;
	private Expression<String> particlestring;
	private Expression<Location> location;
	private Integer marker = 1;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int matchedPattern, Kleenean isDelayed, ParseResult parser) {
		count = (Expression<Number>) e[0];
		particle = (Expression<Particle>) e[1];
		particlestring = (Expression<String>) e[2];
		location = (Expression<Location>) e[3];
		offsetX = (Expression<Number>) e[4];
		offsetY = (Expression<Number>) e[5];
		offsetZ = (Expression<Number>) e[6];
		marker = parser.mark;
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] (create|place|spawn|play|make|summon) [%-number%] [of] (1¦%-particle%|2¦%-string%) [particle[s]] at %locations% [[with] [offset] [by] %-number%[(,| and)] %-number%[(,| and)] %-number%] [and] [[(at|with)] speed %-number% [and] [(at|with)] [particle] data %-number%]";
	}
	@Override
	protected void execute(Event e) {
		Particle p = null;
		Integer num = 1;
		if (count != null) {
			num = count.getSingle(e).intValue();
		}
		if (marker == 1) {
			p = particle.getSingle(e);
		} else {
			try {
				p = Particle.valueOf(particlestring.getSingle(e).replace("\"", "").trim().replace(" ", "_").toUpperCase());
			} catch (IllegalArgumentException error) {
				Bukkit.getConsoleSender().sendMessage(Skellett.cc(Skellett.prefix + "&cUnknown particle " + particlestring.getSingle(e)));
				return;
			}
		}
		if (p == null || num == null) {
			return;
		}
		for (Location loc : location.getAll(e)) {
			if (offsetX != null && offsetY != null && offsetZ != null) {
				loc.getWorld().spawnParticle(p, loc, num, offsetX.getSingle(e).intValue(), offsetY.getSingle(e).intValue(), offsetZ.getSingle(e).intValue());
			} else {
				loc.getWorld().spawnParticle(p, loc, num);
			}
		}
	}
}