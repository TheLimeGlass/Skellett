package com.gmail.thelimeglass.Effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.gmail.thelimeglass.SkellettAPI.SkellettHolograms;
import javax.annotation.Nullable;
import org.bukkit.Location;
import org.bukkit.event.Event;

public class EffCreateHologram
extends Effect {
    private Expression<Location> loc;
    private Expression<String> string;
    private Expression<Integer> ID;
    private Expression<Boolean> glowing;
    private Expression<Boolean> small;

    @SuppressWarnings("unchecked")
	public boolean init(Expression<?>[] e, int arg1, Kleenean arg2, SkriptParser.ParseResult arg3) {
        this.loc = (Expression<Location>) e[0];
        this.string = (Expression<String>) e[1];
        this.ID = (Expression<Integer>) e[2];
        this.glowing = (Expression<Boolean>) e[3];
        this.small = (Expression<Boolean>) e[4];
        return true;
    }

    public String toString(@Nullable Event paramEvent, boolean paramBoolean) {
        return "[skellett] (create|spawn|summon|place) holo[gram] at [location] %location% (with|and) [(text|string)] %string% (with|and) id %integer% [[set] glow[ing]] %-boolean% [[is] small] %-boolean%";
    }

    protected void execute(Event e) {
        SkellettHolograms.createHologram((Location)this.loc.getSingle(e), (String)this.string.getSingle(e), (Integer)this.ID.getSingle(e), (Boolean)this.glowing.getSingle(e), (Boolean)this.small.getSingle(e));
    }
}