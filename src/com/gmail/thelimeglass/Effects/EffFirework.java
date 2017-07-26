package com.gmail.thelimeglass.Effects;

import ch.njol.skript.lang.Effect;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

import java.util.Random;

import javax.annotation.Nullable;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.entity.Firework;
import org.bukkit.event.Event;
import org.bukkit.inventory.meta.FireworkMeta;

public class EffFirework extends Effect {
	
	//launch firework at player timed 0
	//launch firework at player timed 0 coloured red
	//launch "BALL_LARGE" firework at player timed 0
	//launch "BALL_LARGE" firework at player timed 0 coloured red
	//launch "BALL_LARGE" firework at player timed 0 coloured "red", "BLUE", "YELLOW", "PINK" and "white"
	//launch firework at player timed 0 coloured "red", "BLUE", "YELLOW", "PINK" and "white"
	//[skellett] (launch|deploy) [%-string%] firework[s] at %locations% [with] (duration|timed|time) %number% [colo[u]r[ed] (%-strings%|%-color%)]
	
	Random random = new Random();
	private Expression<String> type;
	private Expression<Location> locations;
	private Expression<Number> time;
	private Expression<String> multiColours;
	private Expression<ch.njol.skript.util.Color> colour;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, ParseResult arg3) {
		type = (Expression<String>) e[0];
		locations = (Expression<Location>) e[1];
		time = (Expression<Number>) e[2];
		multiColours = (Expression<String>) e[3];
		colour = (Expression<ch.njol.skript.util.Color>) e[4];
		return true;
	}
	@Override
	public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
		return "[skellett] (launch|deploy) [%-string%] firework[s] at %locations% [with] (duration|timed|time) %number% [colo[u]r[ed] (%-strings%|%-color%)]";
	}
	@Override
	protected void execute(Event e) {
		int r = random.nextInt(17) + 1;
		Color finalColour = getColor(r);
		if (colour != null) {
			ch.njol.skript.util.Color c = ch.njol.skript.util.Color.byName(colour.getSingle(e).toString());
			finalColour = c.getBukkitColor();
		}
		FireworkEffect.Type t = randomType();
		if (type != null) {
			t = FireworkEffect.Type.valueOf(type.getSingle(e).replace("\"", "").trim().replace(" ", "_").toUpperCase());
			try {
				FireworkEffect.Type.valueOf(t.toString().replace("\"", "").trim().replace(" ", "_").toUpperCase());
			} catch (IllegalArgumentException e1) {
				return;
			}
		}
		for (Location l : locations.getAll(e)){
			Firework firework = l.getWorld().spawn(l, Firework.class);
			FireworkMeta meta = firework.getFireworkMeta();
			if (multiColours != null || finalColour == null) {
				for (String s : multiColours.getAll(e)) {
					if (type == null) {
						FireworkEffect.Type randomType = randomType();
						meta.addEffect(FireworkEffect.builder()
							.withColor(ch.njol.skript.util.Color.byName(s.toString()).getBukkitColor())
							.with(randomType)
							.build());
					} else {
						meta.addEffect(FireworkEffect.builder()
							.withColor(ch.njol.skript.util.Color.byName(s.toString()).getBukkitColor())
							.with(t)
							.build());
					}
				}
			} else {
				meta.addEffect(FireworkEffect.builder()
					.withColor(finalColour)
					.with(t)
					.build());
			}
			meta.setPower(time.getSingle(e).intValue());
			firework.setFireworkMeta(meta);
		}
	}
	private FireworkEffect.Type randomType() {
		int i = random.nextInt(5) + 1;
		FireworkEffect.Type type = null;
		if (i == 1) {
			type = FireworkEffect.Type.BALL;
		} else if (i == 2) {
			type = FireworkEffect.Type.BALL_LARGE;
		} else if (i == 3) {
			type = FireworkEffect.Type.BURST;
		} else if (i == 4) {
			type = FireworkEffect.Type.CREEPER;
		} else if (i == 5) {
			type = FireworkEffect.Type.STAR;
		}
		return type;
	}
	private Color getColor(int i) {
		Color c = null;
		if (i == 1) {
			c = Color.AQUA;
		} else if (i == 2) {
			c = Color.BLACK;
		} else if (i == 3) {
			c = Color.BLUE;
		} else if (i == 4) {
			c = Color.FUCHSIA;
		} else if (i == 5) {
			c = Color.GRAY;
		} else if (i == 6) {
			c = Color.GREEN;
		} else if (i == 7) {
			c = Color.LIME;
		} else if (i == 8) {
			c = Color.MAROON;
		} else if (i == 9) {
			c = Color.NAVY;
		} else if (i == 10) {
			c = Color.OLIVE;
		} else if (i == 11) {
			c = Color.ORANGE;
		} else if (i == 12) {
			c = Color.PURPLE;
		} else if (i == 13) {
			c = Color.RED;
		} else if (i == 14) {
			c = Color.SILVER;
		} else if (i == 15) {
			c = Color.TEAL;
		} else if (i == 16) {
			c = Color.WHITE;
		} else if (i == 17) {
			c = Color.YELLOW;	
		}
		return c;
	}
}
